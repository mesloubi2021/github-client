package com.jraska.github.client.users;

import android.support.annotation.NonNull;
import com.jraska.github.client.common.Pair;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

final class GitHubApiUsersRepository implements UsersRepository {
  private final GitHubUsersApi _gitHubUsersApi;
  private final GitHubUserDetailApi _gitHubUserDetailApi;

  public GitHubApiUsersRepository(@NonNull GitHubUsersApi gitHubUsersApi,
                                  @NonNull GitHubUserDetailApi gitHubUserDetailApi) {
    _gitHubUsersApi = gitHubUsersApi;
    _gitHubUserDetailApi = gitHubUserDetailApi;
  }

  @Override public Observable<List<User>> getUsers(int since) {
    return _gitHubUsersApi.getUsers(since).map(this::translateUsers);
  }

  @Override public Observable<UserDetail> getUserDetail(String login) {
    return _gitHubUserDetailApi.getUserDetail(login)
        .subscribeOn(Schedulers.io()) //this has to be here now to run requests in parallel
        .zipWith(_gitHubUserDetailApi.getRepos(login), Pair::new)
        .compose(UserDetailWithReposTranslator.INSTANCE);
  }

  List<User> translateUsers(List<GitHubUser> gitHubUsers) {
    ArrayList<User> users = new ArrayList<>();
    for (GitHubUser gitHubUser : gitHubUsers) {
      users.add(translateUser(gitHubUser));
    }

    return users;
  }

  private User translateUser(GitHubUser gitHubUser) {
    boolean isAdmin = gitHubUser.siteAdmin == null ? false : gitHubUser.siteAdmin;
    return new User(gitHubUser.login, gitHubUser.avatarUrl, isAdmin, gitHubUser.htmlUrl);
  }
}

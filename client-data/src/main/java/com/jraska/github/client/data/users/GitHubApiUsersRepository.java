package com.jraska.github.client.data.users;

import android.support.annotation.NonNull;
import com.jraska.github.client.common.Pair;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UserDetail;
import com.jraska.github.client.users.UsersRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class GitHubApiUsersRepository implements UsersRepository {
  private final GitHubUsersApi gitHubUsersApi;
  private final GitHubUserDetailApi gitHubUserDetailApi;

  public GitHubApiUsersRepository(@NonNull GitHubUsersApi gitHubUsersApi,
                                  @NonNull GitHubUserDetailApi gitHubUserDetailApi) {
    this.gitHubUsersApi = gitHubUsersApi;
    this.gitHubUserDetailApi = gitHubUserDetailApi;
  }

  @Override public Single<List<User>> getUsers(int since) {
    return gitHubUsersApi.getUsers(since).map(this::translateUsers);
  }

  @Override public Single<UserDetail> getUserDetail(String login) {
    return gitHubUserDetailApi.getUserDetail(login)
        .subscribeOn(Schedulers.io()) //this has to be here now to run requests in parallel
        .zipWith(gitHubUserDetailApi.getRepos(login), Pair::new)
        .compose(UserDetailWithReposTranslator.INSTANCE);
  }

  List<User> translateUsers(List<GitHubUser> gitHubUsers) {
    ArrayList<User> users = new ArrayList<>();
    for (GitHubUser gitHubUser : gitHubUsers) {
      users.add(translateUser(gitHubUser));
    }

    return Collections.unmodifiableList(users);
  }

  private User translateUser(GitHubUser gitHubUser) {
    boolean isAdmin = gitHubUser.siteAdmin == null ? false : gitHubUser.siteAdmin;
    return new User(gitHubUser.login, gitHubUser.avatarUrl, isAdmin, gitHubUser.htmlUrl);
  }
}

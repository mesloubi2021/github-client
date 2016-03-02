package com.jraska.github.client.users;

import android.support.annotation.NonNull;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class UsersApiImpl implements UsersApi {
  private final GitHubUsersApi _gitHubUsersApi;

  public UsersApiImpl(@NonNull GitHubUsersApi gitHubUsersApi) {
    _gitHubUsersApi = gitHubUsersApi;
  }

  @Override public Observable<List<User>> getUsers(int since) {
    return _gitHubUsersApi.getUsers(since).map(this::translateUsers);
  }

  List<User> translateUsers(List<GitHubUser> gitHubUsers) {
    ArrayList<User> users = new ArrayList<>();
    for (GitHubUser gitHubUser : gitHubUsers) {
      users.add(translateUser(gitHubUser));
    }

    return users;
  }

  private User translateUser(GitHubUser gitHubUser) {
    return new User(gitHubUser.login);
  }
}

package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.io.Serializable;

public final class User implements Serializable {
  @NonNull public final String login;
  @NonNull public final String avatarUrl;
  @NonNull public final String gitHubUrl;

  public final boolean isAdmin;

  public User(@NonNull String login, @NonNull String avatarUrl,
              boolean isAdmin, @NonNull String gitHubUrl) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.isAdmin = isAdmin;
    this.gitHubUrl = gitHubUrl;
  }
}

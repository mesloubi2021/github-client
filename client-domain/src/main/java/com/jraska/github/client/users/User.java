package com.jraska.github.client.users;

import java.io.Serializable;

public final class User implements Serializable {
  public final String login;
  public final String avatarUrl;
  public final String gitHubUrl;

  public final boolean isAdmin;

  public User(String login, String avatarUrl,
              boolean isAdmin, String gitHubUrl) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.isAdmin = isAdmin;
    this.gitHubUrl = gitHubUrl;
  }
}

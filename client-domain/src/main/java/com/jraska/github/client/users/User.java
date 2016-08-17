package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.io.Serializable;

public final class User implements Serializable {
  @NonNull public final String _login;
  @NonNull public final String _avatarUrl;
  @NonNull public final String _gitHubUrl;

  public final boolean _isAdmin;

  public User(@NonNull String login, @NonNull String avatarUrl,
              boolean isAdmin, @NonNull String gitHubUrl) {
    _login = login;
    _avatarUrl = avatarUrl;
    _isAdmin = isAdmin;
    _gitHubUrl = gitHubUrl;
  }
}

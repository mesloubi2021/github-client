package com.jraska.github.client.users;

import android.support.annotation.NonNull;

public final class User {
  @NonNull public final String _login;
  @NonNull public final String _avatarUrl;

  public final boolean _isAdmin;

  public User(@NonNull String login, @NonNull String avatarUrl, boolean isAdmin) {
    _login = login;
    _avatarUrl = avatarUrl;
    _isAdmin = isAdmin;
  }
}

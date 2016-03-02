package com.jraska.github.client.users;

import android.support.annotation.NonNull;

public final class User {
  @NonNull
  public final String _login;

  public User(@NonNull String login) {
    _login = login;
  }
}

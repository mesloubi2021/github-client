package com.jraska.github.client.users;

import android.support.annotation.NonNull;

public class User {
  @NonNull
  public final String _login;

  public User(@NonNull String login) {
    _login = login;
  }
}

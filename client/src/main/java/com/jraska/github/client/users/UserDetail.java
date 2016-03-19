package com.jraska.github.client.users;

import android.support.annotation.NonNull;

public class UserDetail {
  @NonNull public final UserStats _basicStats;

  public UserDetail(@NonNull UserStats basicStats) {
    _basicStats = basicStats;
  }
}

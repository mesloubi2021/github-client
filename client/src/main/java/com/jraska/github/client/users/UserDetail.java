package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class UserDetail {
  @NonNull public final UserStats _basicStats;
  @NonNull public final List<Repo> _popularRepos;

  public UserDetail(@NonNull UserStats basicStats, @NonNull List<Repo> popularRepos) {
    _basicStats = basicStats;
    _popularRepos = Collections.unmodifiableList(popularRepos);
  }
}

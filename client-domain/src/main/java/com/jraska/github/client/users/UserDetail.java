package com.jraska.github.client.users;

import android.support.annotation.NonNull;
import com.jraska.github.client.users.Repo;
import com.jraska.github.client.users.UserStats;

import java.util.Collections;
import java.util.List;

public class UserDetail {
  @NonNull public final UserStats _basicStats;
  @NonNull public final List<Repo> _popularRepos;
  @NonNull public final List<Repo> _contributedRepos;

  public UserDetail(@NonNull UserStats basicStats, @NonNull List<Repo> popularRepos, @NonNull List<Repo> contributedRepos) {
    _basicStats = basicStats;
    _popularRepos = Collections.unmodifiableList(popularRepos);
    _contributedRepos = Collections.unmodifiableList(contributedRepos);
  }
}

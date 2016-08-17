package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class UserDetail {
  @NonNull public final UserStats basicStats;
  @NonNull public final List<Repo> popularRepos;
  @NonNull public final List<Repo> contributedRepos;

  public UserDetail(@NonNull UserStats basicStats, @NonNull List<Repo> popularRepos,
                    @NonNull List<Repo> contributedRepos) {
    this.basicStats = basicStats;
    this.popularRepos = Collections.unmodifiableList(popularRepos);
    this.contributedRepos = Collections.unmodifiableList(contributedRepos);
  }
}

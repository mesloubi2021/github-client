package com.jraska.github.client.users;

import java.util.Collections;
import java.util.List;

public class UserDetail {
  public final UserStats basicStats;
  public final List<Repo> popularRepos;
  public final List<Repo> contributedRepos;

  public UserDetail(UserStats basicStats, List<Repo> popularRepos,
                    List<Repo> contributedRepos) {
    this.basicStats = basicStats;
    this.popularRepos = Collections.unmodifiableList(popularRepos);
    this.contributedRepos = Collections.unmodifiableList(contributedRepos);
  }
}

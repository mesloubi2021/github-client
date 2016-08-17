package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.util.Date;

public class UserStats {
  public final int followers;
  public final int following;
  public final int publicRepos;
  @NonNull public final Date joined;

  public UserStats(int followers, int following, int publicRepos, @NonNull Date joined) {
    this.followers = followers;
    this.following = following;
    this.publicRepos = publicRepos;
    this.joined = joined;
  }
}

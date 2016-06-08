package com.jraska.github.client.users;

import android.support.annotation.NonNull;

import java.util.Date;

public class UserStats {
  public final int _followers;
  public final int _following;
  public final int _publicRepos;
  @NonNull public final Date _joined;

  public UserStats(int followers, int following, int publicRepos, @NonNull Date joined) {
    _followers = followers;
    _following = following;
    _publicRepos = publicRepos;
    _joined = joined;
  }
}

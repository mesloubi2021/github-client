package com.jraska.github.client.users;

import android.support.annotation.NonNull;

public class Repo {
  public final String _name;
  public final String _description;
  public final int _watchers;
  public final int _stars;
  public final int _forks;
  public final int _size;

  public Repo(@NonNull String name, @NonNull String description, int watchers, int stars, int forks, int size) {
    _name = name;
    _description = description;
    _watchers = watchers;
    _stars = stars;
    _forks = forks;
    _size = size;
  }
}

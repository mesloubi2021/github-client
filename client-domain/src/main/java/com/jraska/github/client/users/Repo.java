package com.jraska.github.client.users;

public class Repo {
  public final String name;
  public final String description;
  public final int watchers;
  public final int stars;
  public final int forks;
  public final int size;

  public Repo(String name, String description, int watchers, int stars, int forks, int size) {
    this.name = name;
    this.description = description;
    this.watchers = watchers;
    this.stars = stars;
    this.forks = forks;
    this.size = size;
  }
}

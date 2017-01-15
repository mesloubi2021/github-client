package com.jraska.github.client.common;

public final class Preconditions {
  private Preconditions() {
  }

  public static <T> T argNotNull(T arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }

    return arg;
  }
}

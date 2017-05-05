package com.jraska.github.client.time;

import java.util.concurrent.TimeUnit;

public final class RealTimeProvider implements TimeProvider {
  public static final RealTimeProvider INSTANCE = new RealTimeProvider();

  private RealTimeProvider() {
  }

  @Override public long elapsed() {
    return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
  }
}

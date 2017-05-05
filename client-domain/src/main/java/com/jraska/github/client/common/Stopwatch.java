package com.jraska.github.client.common;

import com.jraska.github.client.time.RealTimeProvider;
import com.jraska.github.client.time.TimeProvider;

public final class Stopwatch {

  private static final long NOT_SET = -1;

  public static Stopwatch create() {
    return new Stopwatch(RealTimeProvider.INSTANCE);
  }

  public static Stopwatch started() {
    return create().start();
  }

  private final TimeProvider timeProvider;

  private long start = NOT_SET;
  private long stop = NOT_SET;

  Stopwatch(TimeProvider timeProvider) {
    this.timeProvider = Preconditions.argNotNull(timeProvider);
  }

  public Stopwatch start() {
    if (running()) {
      return this;
    }

    if (stop == NOT_SET) {
      start = timeProvider.elapsed();
    } else {
      stop = NOT_SET;
    }

    return this;
  }

  public Stopwatch stop() {
    if (!running()) {
      return this;
    }

    stop = timeProvider.elapsed();
    return this;
  }

  public Stopwatch reset() {
    start = NOT_SET;
    stop = NOT_SET;
    return this;
  }

  public Stopwatch restart() {
    return reset().start();
  }

  public boolean running() {
    return start != NOT_SET && stop == NOT_SET;
  }

  public long time() {
    if (running()) {
      return timeProvider.elapsed() - start;
    } else {
      return stop - start;
    }
  }
}

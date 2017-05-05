package com.jraska.github.client.time;

public final class TestTimeProvider implements TimeProvider {
  private long elapsed;

  public TestTimeProvider() {
    this(0);
  }

  public TestTimeProvider(long elapsed) {
    this.elapsed = elapsed;
  }

  @Override public long elapsed() {
    return elapsed;
  }

  public TestTimeProvider elapsed(long elapsedMillis) {
    this.elapsed = elapsedMillis;
    return this;
  }

  public TestTimeProvider advanceTime(long difference) {
    if (difference < 0) {
      throw new IllegalArgumentException("Can only advance time forward");
    }

    elapsed += difference;
    return this;
  }
}
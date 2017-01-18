package com.jraska.github.client.logging;

import timber.log.Timber;

public final class TimberLogger implements Logger {
  @Override public TimberLogger e(Throwable error, String message, Object... args) {
    Timber.tag(null).e(error, message, args);
    return this;
  }

  @Override public TimberLogger w(Throwable error, String message, Object... args) {
    Timber.tag(null).w(error, message, args);
    return this;
  }

  @Override public TimberLogger i(String message, Object... args) {
    Timber.tag(null).i(message, args);
    return this;
  }

  @Override public TimberLogger d(String message, Object... args) {
    Timber.tag(null).d(message, args);
    return this;
  }

  @Override public TimberLogger v(String message, Object... args) {
    Timber.tag(null).v(message, args);
    return this;
  }
}

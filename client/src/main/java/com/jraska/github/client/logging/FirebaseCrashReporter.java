package com.jraska.github.client.logging;

public final class FirebaseCrashReporter implements CrashReporter {
  private final FirebaseCrashProxy crashProxy;

  public FirebaseCrashReporter() {
    this(new FirebaseCrashProxy());
  }

  FirebaseCrashReporter(FirebaseCrashProxy crashProxy) {
    this.crashProxy = crashProxy;
  }

  @Override public CrashReporter report(Throwable error, String... messages) {
    for (String message : messages) {
      crashProxy.log(message);
    }

    crashProxy.report(error);
    return this;
  }
}

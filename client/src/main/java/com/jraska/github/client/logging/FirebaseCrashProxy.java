package com.jraska.github.client.logging;

import com.google.firebase.crash.FirebaseCrash;

public final class FirebaseCrashProxy implements CrashReporter {
  @Override public CrashReporter log(String error) {
    FirebaseCrash.log(error);
    return this;
  }

  @Override public CrashReporter report(Throwable error) {
    FirebaseCrash.report(error);
    return this;
  }
}

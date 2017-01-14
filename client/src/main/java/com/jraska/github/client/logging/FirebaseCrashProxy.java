package com.jraska.github.client.logging;

import com.google.firebase.crash.FirebaseCrash;

public final class FirebaseCrashProxy implements CrashReporter {
  @Override public void log(String error) {
    FirebaseCrash.log(error);
  }

  @Override public void report(Throwable error) {
    FirebaseCrash.report(error);
  }
}

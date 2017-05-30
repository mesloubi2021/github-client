package com.jraska.github.client.logging;

import com.google.firebase.crash.FirebaseCrash;

class FirebaseCrashProxy {
  void log(String message) {
    FirebaseCrash.log(message);
  }

  void report(Throwable error) {
    FirebaseCrash.report(error);
  }
}

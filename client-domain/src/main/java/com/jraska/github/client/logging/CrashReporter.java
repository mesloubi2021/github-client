package com.jraska.github.client.logging;

public interface CrashReporter {
  void log(String error);

  void report(Throwable error);
}

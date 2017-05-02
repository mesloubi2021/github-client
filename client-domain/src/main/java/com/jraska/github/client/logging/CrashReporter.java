package com.jraska.github.client.logging;

public interface CrashReporter {
  CrashReporter log(String error);

  CrashReporter report(Throwable error);
}

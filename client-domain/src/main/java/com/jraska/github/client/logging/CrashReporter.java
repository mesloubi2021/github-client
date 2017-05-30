package com.jraska.github.client.logging;

public interface CrashReporter {
  CrashReporter report(Throwable error, String... messages);
}

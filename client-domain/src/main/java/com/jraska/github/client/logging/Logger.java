package com.jraska.github.client.logging;

public interface Logger {
  Logger e(Throwable error, String message, Object... args);

  Logger w(Throwable error, String message, Object... args);

  Logger i(String message, Object... args);

  Logger d(String message, Object... args);

  Logger v(String message, Object... args);
}

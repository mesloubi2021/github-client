package com.jraska.github.client.logging;

import android.util.Log;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ErrorReportTree extends Timber.Tree {
  private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
  private static final int CALL_STACK_INDEX = 6;

  private final CrashReporter crashReporter;

  @Inject ErrorReportTree(CrashReporter crashReporter) {
    this.crashReporter = crashReporter;
  }

  @Override protected boolean isLoggable(String tag, int priority) {
    return priority >= Log.ERROR;
  }

  @Override protected void log(int priority, String tag, String message, Throwable error) {
    if (tag == null) {
      tag = createStackTag();
    }

    if (tag != null || message != null) {
      crashReporter.log(tag + "/" + message);
    }

    if (error != null) {
      crashReporter.report(error);
    }
  }

  private String createStackElementTag(StackTraceElement element) {
    String tag = element.getClassName();
    Matcher m = ANONYMOUS_CLASS.matcher(tag);
    if (m.find()) {
      tag = m.replaceAll("");
    }
    return tag.substring(tag.lastIndexOf('.') + 1);
  }

  private String createStackTag() {
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    if (stackTrace.length <= CALL_STACK_INDEX) {
      return null;
    }
    return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
  }
}

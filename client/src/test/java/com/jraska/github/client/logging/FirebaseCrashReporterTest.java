package com.jraska.github.client.logging;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FirebaseCrashReporterTest {
  @Test
  public void whenNoMessageArgument_thenPasses() {
    FirebaseCrashProxy crashProxyMock = mock(FirebaseCrashProxy.class);
    FirebaseCrashReporter crashReporter = new FirebaseCrashReporter(crashProxyMock);
    RuntimeException exception = new RuntimeException();

    crashReporter.report(exception);

    verify(crashProxyMock, only()).report(eq(exception));
  }
}

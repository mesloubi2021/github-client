package com.jraska.github.client;

import android.app.Application;
import timber.log.Timber;

public class GitHubClientApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
  }
}

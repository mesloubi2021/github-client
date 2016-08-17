package com.jraska.github.client;

import android.app.Application;
import timber.log.Timber;

public class GitHubClientApp extends Application {
  private AppComponent appComponent;

  public AppComponent component() {
    return appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();

    Timber.plant(new Timber.DebugTree());
  }
}

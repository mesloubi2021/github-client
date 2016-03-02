package com.jraska.github.client;

import android.app.Application;
import timber.log.Timber;

public class GitHubClientApp extends Application {
  private AppComponent _appComponent;

  public AppComponent getComponent() {
    return _appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    _appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();

    Timber.plant(new Timber.DebugTree());
  }
}

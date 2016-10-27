package com.jraska.github.client;

import android.app.Application;
import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.http.DaggerHttpComponent;
import com.jraska.github.client.http.HttpComponent;
import com.jraska.github.client.http.HttpDependenciesModule;
import timber.log.Timber;

import java.io.File;

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
        .httpComponent(httpComponent())
        .build();

    Timber.plant(new Timber.DebugTree());
  }

  protected HttpComponent httpComponent() {
    HttpDependenciesModule dependenciesModule = new HttpDependenciesModule(
        new AppBuildConfig(BuildConfig.DEBUG), new File(getCacheDir(), "network"));

    return DaggerHttpComponent.builder()
        .httpDependenciesModule(dependenciesModule)
        .build();
  }
}

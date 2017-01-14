package com.jraska.github.client;

import android.app.Application;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.http.DaggerHttpComponent;
import com.jraska.github.client.http.HttpComponent;
import com.jraska.github.client.http.HttpDependenciesModule;
import com.jraska.github.client.logging.ErrorReportTree;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.File;

public class GitHubClientApp extends Application {
  private AppComponent appComponent;

  @Inject FirebaseAnalytics analytics;
  @Inject ErrorReportTree errorReportTree;

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

    appComponent.inject(this);

    Timber.plant(errorReportTree);
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    logAppCreateEvent();
  }

  protected HttpComponent httpComponent() {
    HttpDependenciesModule dependenciesModule = new HttpDependenciesModule(
        new AppBuildConfig(BuildConfig.DEBUG), new File(getCacheDir(), "network"));

    return DaggerHttpComponent.builder()
        .httpDependenciesModule(dependenciesModule)
        .build();
  }

  private void logAppCreateEvent() {
    Bundle bundle = new Bundle();
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "app_create");
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "app_create");
    analytics.logEvent("app_create", bundle);
  }
}

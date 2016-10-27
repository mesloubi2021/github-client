package com.jraska.github.client.http;

import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.logging.Logger;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import timber.log.Timber;

import java.io.File;

@Module
public class HttpDependenciesModule {
  private final AppBuildConfig config;
  private File cacheDir;

  public HttpDependenciesModule(AppBuildConfig config, File cacheDir) {
    this.config = config;
    this.cacheDir = cacheDir;
  }

  @Provides AppBuildConfig config() {
    return config;
  }

  @Provides Logger timberLogger() {
    return message -> Timber.tag("Network").v(message);
  }

  @Provides File cacheDir() {
    return cacheDir;
  }
}

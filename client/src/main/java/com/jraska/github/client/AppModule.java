package com.jraska.github.client;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.logging.Logger;
import com.jraska.github.client.network.Network;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import timber.log.Timber;

import javax.inject.Singleton;
import java.io.File;

@Module
public class AppModule {
  private final GitHubClientApp _app;

  public AppModule(@NonNull GitHubClientApp app) {
    _app = app;
  }

  @Singleton @Provides Context provideContext() {
    return _app;
  }

  @Singleton @Provides Picasso picasso(Context context) {
    return Picasso.with(context);
  }

  @Provides @Network File provideNetworkCacheDir(Context context) {
    return context.getCacheDir();
  }

  @Provides @Network Logger provideTimberLogger() {
    return message -> Timber.tag("Network").v(message);
  }

  @Provides @Reusable AppBuildConfig provideConfig() {
    return new AppBuildConfig(BuildConfig.DEBUG);
  }
}

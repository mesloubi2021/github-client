package com.jraska.github.client;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.logging.Logger;
import com.jraska.github.client.network.Network;
import com.jraska.github.client.rx.AndroidAppSchedulers;
import com.jraska.github.client.rx.AppSchedulers;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import okhttp3.OkHttpClient;
import timber.log.Timber;

import javax.inject.Singleton;
import java.io.File;

@Module
public class AppModule {
  private final GitHubClientApp app;

  public AppModule(@NonNull GitHubClientApp app) {
    this.app = app;
  }

  @Provides Context provideContext() {
    return app;
  }

  @Provides @Singleton Picasso picasso(Context context, Downloader downloader) {
    return new Picasso.Builder(context)
        .downloader(downloader)
        .build();
  }


  @Provides @Singleton Downloader downloader(OkHttpClient.Builder builder) {
    return new OkHttp3Downloader(builder.build());
  }

  @Provides @Network File provideNetworkCacheDir(Context context) {
    return context.getCacheDir();
  }

  @Provides @Reusable @Network Logger timberLogger() {
    return message -> Timber.tag("Network").v(message);
  }

  @Provides @Reusable AppBuildConfig provideConfig() {
    return new AppBuildConfig(BuildConfig.DEBUG);
  }

  @Provides @Singleton AppSchedulers schedulers() {
    return AndroidAppSchedulers.get();
  }
}

package com.jraska.github.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.logging.VerboseLogger;
import com.jraska.github.client.rx.AppSchedulers;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class AppModule {
  private final GitHubClientApp app;

  public AppModule(@NonNull GitHubClientApp app) {
    this.app = app;
  }

  @Provides Context provideContext() {
    return app;
  }

  // TODO: 16/01/17 Configure Fresco
//  @Provides
//  Downloader downloader(Context context, Logger logger, AppBuildConfig config) {
//    OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//    if (config.debug) {
//      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::v)
//          .setLevel(HttpLoggingInterceptor.Level.BASIC);
//      builder.addInterceptor(loggingInterceptor);
//    }
//
//    File cacheDir = new File(context.getCacheDir(), "images");
//    Cache imageCache = new Cache(cacheDir, 4 * 1024 * 1024);
//    builder.cache(imageCache);
//
//    return new OkHttp3Downloader(builder.build());
//  }

  @Provides VerboseLogger timberLogger() {
    return message -> Timber.tag("Network").v(message);
  }

  @Provides @Reusable AppBuildConfig provideConfig() {
    return new AppBuildConfig(BuildConfig.DEBUG);
  }

  @Provides @PerApp AppSchedulers schedulers() {
    return new AppSchedulers(AndroidSchedulers.mainThread(),
        Schedulers.io(), Schedulers.computation());
  }

  @Provides @PerApp LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }
}

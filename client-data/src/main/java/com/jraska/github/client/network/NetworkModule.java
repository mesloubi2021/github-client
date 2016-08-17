package com.jraska.github.client.network;

import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.logging.Logger;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.inject.Singleton;
import java.io.File;

@Module
public class NetworkModule {
  @Provides @Singleton
  OkHttpClient provideOkHttpClient(@Network File cacheDir, OkHttpClient.Builder builder) {
    Cache cache = new Cache(cacheDir, 1024 * 1024 * 4);
    builder.cache(cache);

    return builder.build();
  }

  @Provides OkHttpClient.Builder provideBasicBuilder(@Network Logger logger, AppBuildConfig config) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();

    if (config.debug) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::v);
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
      builder.addInterceptor(loggingInterceptor);
    }

    return builder;
  }
}

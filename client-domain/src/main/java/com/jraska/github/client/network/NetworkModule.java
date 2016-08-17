package com.jraska.github.client.network;

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
  OkHttpClient provideOkHttpClient(@Network File cacheDir, @Network Logger logger) {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::v);
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

    Cache cache = new Cache(cacheDir, 1024 * 1024 * 4);

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build();

    return client;
  }
}

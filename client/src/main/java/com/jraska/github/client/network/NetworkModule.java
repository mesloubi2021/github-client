package com.jraska.github.client.network;

import android.content.Context;
import com.jraska.github.client.dagger.PerApp;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import java.io.File;

@Module
public class NetworkModule {
  @Provides @PerApp OkHttpClient provideOkHttpClient(Context context) {
    HttpLoggingInterceptor.Logger logger = message -> Timber.tag("OkHttp").v(message);
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

    File cacheDir = context.getCacheDir();
    Cache cache = new Cache(cacheDir, 1024 * 1024 * 4);

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build();

    return client;
  }
}

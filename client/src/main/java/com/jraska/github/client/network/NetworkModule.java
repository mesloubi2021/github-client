package com.jraska.github.client.network;

import com.jraska.github.client.dagger.PerApp;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {
  @Provides @PerApp OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }
}

package com.jraska.github.client;

import android.content.Context;
import android.view.LayoutInflater;
import com.jraska.github.client.dagger.PerApp;
import dagger.Module;
import dagger.Provides;

@Module
public class SystemServicesModule {
  @Provides @PerApp LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }
}

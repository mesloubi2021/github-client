package com.jraska.github.client;

import android.content.Context;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SystemServicesModule {
  @Provides @PerApp LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }
}

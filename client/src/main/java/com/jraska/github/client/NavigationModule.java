package com.jraska.github.client;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class NavigationModule {
  @Provides
  public static Navigator provideNavigator(Activity activity) {
    return new DeepLinkNavigator(new RealDeepLinkLauncher(activity), new RealWebLinkLauncher(activity));
  }
}

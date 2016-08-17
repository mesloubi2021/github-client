package com.jraska.github.client.ui;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final BaseActivity activity;

  ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides Activity provideActivity(BaseActivity baseActivity) {
    return baseActivity;
  }

  @Provides FragmentManager provideFragmentManager(BaseActivity baseActivity) {
    return baseActivity.getSupportFragmentManager();
  }

  @Provides BaseActivity provideBaseActivity() {
    return activity;
  }
}

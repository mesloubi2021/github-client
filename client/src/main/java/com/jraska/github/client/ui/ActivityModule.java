package com.jraska.github.client.ui;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final BaseActivity _activity;

  public ActivityModule(BaseActivity activity) {
    _activity = activity;
  }

  @Provides Activity provideActivity(BaseActivity baseActivity) {
    return baseActivity;
  }

  @Provides BaseActivity provideBaseActivity() {
    return _activity;
  }
}

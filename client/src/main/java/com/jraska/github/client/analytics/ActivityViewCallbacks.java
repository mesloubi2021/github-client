package com.jraska.github.client.analytics;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public final class ActivityViewCallbacks implements Application.ActivityLifecycleCallbacks {
  private final ActivityViewTrigger viewTrigger;

  public ActivityViewCallbacks(ActivityViewTrigger viewTrigger) {
    this.viewTrigger = viewTrigger;
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override public void onActivityStarted(Activity activity) {
  }

  @Override public void onActivityResumed(Activity activity) {
    viewTrigger.reportCurrent(activity);
  }

  @Override public void onActivityPaused(Activity activity) {
  }

  @Override public void onActivityStopped(Activity activity) {
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  @Override public void onActivityDestroyed(Activity activity) {
  }
}

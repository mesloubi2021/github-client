package com.jraska.github.client.analytics;

import android.app.Activity;

public interface AnalyticsExtractor<TActivity extends Activity> {
  ActivityAnalytics analytics(TActivity activity);

  class Simple implements AnalyticsExtractor<Activity> {
    static Simple INSTANCE = new Simple();

    @Override public ActivityAnalytics analytics(Activity activity) {
      return ActivityAnalytics.create(activity.getClass().getSimpleName());
    }
  }
}

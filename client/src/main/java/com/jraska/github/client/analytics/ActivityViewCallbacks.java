package com.jraska.github.client.analytics;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.common.DeveloperError;

import java.util.Map;

public final class ActivityViewCallbacks implements Application.ActivityLifecycleCallbacks {
  private final FirebaseAnalytics analytics;
  private final Map<Class, AnalyticsExtractor<?>> extractorMap;

  public ActivityViewCallbacks(FirebaseAnalytics analytics, Map<Class, AnalyticsExtractor<?>> extractorMap) {
    this.analytics = analytics;
    this.extractorMap = extractorMap;
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override public void onActivityStarted(Activity activity) {
  }

  @SuppressWarnings("unchecked")
  @Override public void onActivityResumed(Activity activity) {
    AnalyticsExtractor<Activity> extractor = (AnalyticsExtractor<Activity>) extractorMap.get(activity.getClass());
    if (extractor == null) {
      throw new DeveloperError(AnalyticsExtractor.class.getSimpleName()
          + " is not registered for " + activity.getClass());
    }

    ActivityAnalytics analytics = extractor.analytics(activity);
    this.analytics.setCurrentScreen(activity, analytics.screenName, null);

    Bundle parameters = new Bundle();
    for (Map.Entry<String, String> analytic : analytics.properties.entrySet()) {
      parameters.putString(analytic.getKey(), analytic.getValue());
    }
    this.analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, parameters);
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

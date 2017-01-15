package com.jraska.github.client.analytics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.common.DeveloperError;
import com.jraska.github.client.common.Preconditions;

import java.util.Map;

public final class ActivityViewTrigger {
  private final FirebaseAnalytics analytics;
  private final Map<Class, AnalyticsExtractor<?>> extractorMap;

  ActivityViewTrigger(FirebaseAnalytics analytics, Map<Class, AnalyticsExtractor<?>> extractorMap) {
    this.analytics = Preconditions.argNotNull(analytics);
    this.extractorMap = Preconditions.argNotNull(extractorMap);
  }

  public void reportCurrent(@NonNull Activity activity) {
    AnalyticsExtractor<Activity> extractor = extractor(activity);
    ActivityAnalytics analytics = extractor.analytics(activity);

    this.analytics.setCurrentScreen(activity, analytics.screenName, null);
    reportProperties(analytics.properties);
  }

  private void reportProperties(Map<String, String> properties) {
    Bundle parameters = new Bundle();
    for (Map.Entry<String, String> analytic : properties.entrySet()) {
      parameters.putString(analytic.getKey(), analytic.getValue());
    }

    analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, parameters);
  }

  private AnalyticsExtractor<Activity> extractor(Activity activity) {
    @SuppressWarnings("unchecked")
    AnalyticsExtractor<Activity> extractor = (AnalyticsExtractor<Activity>) extractorMap.get(activity.getClass());
    if (extractor == null) {
      throw new DeveloperError(AnalyticsExtractor.class.getSimpleName()
          + " is not registered for " + activity.getClass());
    }
    return extractor;
  }
}

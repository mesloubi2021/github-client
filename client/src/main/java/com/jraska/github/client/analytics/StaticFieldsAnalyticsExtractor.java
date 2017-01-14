package com.jraska.github.client.analytics;

import android.app.Activity;

public final class StaticFieldsAnalyticsExtractor implements AnalyticsExtractor<Activity> {
  public final String screenName;
  public final String url;
  public final String name;

  StaticFieldsAnalyticsExtractor(String screenName, String url, String name) {
    this.screenName = screenName;
    this.url = url;
    this.name = name;
  }

  @Override public ActivityAnalytics analytics(Activity activity) {
    return ActivityAnalytics.builder(screenName)
        .addProperty("url", url)
        .addProperty("name", name)
        .build();
  }
}

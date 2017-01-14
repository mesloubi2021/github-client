package com.jraska.github.client.analytics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ActivityAnalytics {
  final String screenName;
  final Map<String, String> properties;

  private ActivityAnalytics(String screenName, Map<String, String> properties) {
    this.screenName = screenName;
    this.properties = properties;
  }

  public static ActivityAnalytics create(String screenName) {
    return new ActivityAnalytics(screenName, Collections.emptyMap());
  }

  public static Builder builder(String screenName) {
    return new Builder(screenName);
  }

  public static class Builder {
    private final String screenName;
    private final Map<String, String> properties = new HashMap<>();

    Builder(String screenName) {
      this.screenName = screenName;
    }

    public Builder addProperty(String name, String value) {
      properties.put(name, value);
      return this;
    }

    public ActivityAnalytics build() {
      return new ActivityAnalytics(screenName, Collections.unmodifiableMap(properties));
    }
  }
}

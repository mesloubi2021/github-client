package com.jraska.github.client.analytics;

public interface EventAnalytics {
  void report(AnalyticsEvent event);

  EventAnalytics EMPTY = event -> {};
}

package com.jraska.github.client;

import com.jraska.github.client.analytics.AnalyticsEvent;
import com.jraska.github.client.analytics.EventAnalytics;
import com.jraska.github.client.logging.CrashReporter;
import okhttp3.HttpUrl;

import javax.inject.Inject;

public final class DeepLinkHandler {
  private final DeepLinkLauncher linkLauncher;
  private final WebLinkLauncher fallbackLauncher;
  private final CrashReporter crashReporter;
  private final EventAnalytics eventAnalytics;

  @Inject
  public DeepLinkHandler(DeepLinkLauncher linkLauncher, WebLinkLauncher fallbackLauncher,
                         CrashReporter crashReporter, EventAnalytics eventAnalytics) {
    this.linkLauncher = linkLauncher;
    this.fallbackLauncher = fallbackLauncher;
    this.crashReporter = crashReporter;
    this.eventAnalytics = eventAnalytics;
  }

  public void handleDeepLink(HttpUrl deepLink) {
    AnalyticsEvent event = AnalyticsEvent.builder("deep_link_received")
      .addProperty("deep_link", deepLink.toString())
      .build();
    eventAnalytics.report(event);

    try {
      linkLauncher.launch(deepLink);
    } catch (IllegalArgumentException ex) {
      crashReporter.report(ex, "Invalid deep link", deepLink.toString());
      fallbackLauncher.launch(deepLink);
    }
  }
}

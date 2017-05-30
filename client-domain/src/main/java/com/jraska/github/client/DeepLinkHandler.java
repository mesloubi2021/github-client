package com.jraska.github.client;

import com.jraska.github.client.analytics.AnalyticsEvent;
import com.jraska.github.client.analytics.EventAnalytics;
import com.jraska.github.client.logging.CrashReporter;

import javax.inject.Inject;

import okhttp3.HttpUrl;

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

    boolean launch = linkLauncher.launch(deepLink);
    if (!launch) {
      crashReporter.log("Unhandled deep link: " + deepLink);
      fallbackLauncher.launch(deepLink);
    }
  }
}

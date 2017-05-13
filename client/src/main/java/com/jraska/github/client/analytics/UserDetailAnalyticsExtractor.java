package com.jraska.github.client.analytics;

import com.jraska.github.client.ui.UserDetailActivity;

final class UserDetailAnalyticsExtractor implements AnalyticsExtractor<UserDetailActivity> {
  @Override public ActivityAnalytics analytics(UserDetailActivity activity) {
    String login = activity.login();

    return ActivityAnalytics.builder("UserDetail")
        .addProperty("user_login", login)
        .build();
  }
}

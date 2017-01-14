package com.jraska.github.client.analytics;

import android.app.Application;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.ui.UserDetailActivity;
import com.jraska.github.client.ui.UsersActivity;
import com.jraska.github.client.users.UserDetail;

import javax.inject.Inject;
import java.util.HashMap;

public final class CallbacksFactory {
  private final FirebaseAnalytics analytics;

  @Inject CallbacksFactory(FirebaseAnalytics analytics) {
    this.analytics = analytics;
  }

  public Application.ActivityLifecycleCallbacks createViewCallbacks() {
    HashMap<Class, AnalyticsExtractor<?>> extractorsMap = new HashMap<>();

    extractorsMap.put(UsersActivity.class, AnalyticsExtractor.Simple.INSTANCE);
    extractorsMap.put(UserDetailActivity.class, new UserDetailAnalyticsExtractor());
//    extractorsMap.put(SomeOtherActivity.class, new StaticFieldsAnalyticsExtractor("nameUrlScreen",
//        "http://name.com", "nameMe"));

    return new ActivityViewCallbacks(analytics, extractorsMap);
  }
}

package com.jraska.github.client.analytics;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.PerApp;
import com.jraska.github.client.ui.UserDetailActivity;
import com.jraska.github.client.ui.UsersActivity;
import dagger.Module;
import dagger.Provides;

import java.util.HashMap;

@Module
public abstract class AnalyticsModule {
  @Provides @PerApp
  public static ActivityViewReporter viewTrigger(FirebaseAnalytics analytics) {
    HashMap<Class, AnalyticsExtractor<?>> extractorsMap = new HashMap<>();

    extractorsMap.put(UsersActivity.class, AnalyticsExtractor.Simple.INSTANCE);
    extractorsMap.put(UserDetailActivity.class, new UserDetailAnalyticsExtractor());
//    extractorsMap.put(SomeOtherActivity.class, new StaticFieldsAnalyticsExtractor("nameUrlScreen",
//        "http://name.com", "nameMe"));

    return new ActivityViewReporter(analytics, extractorsMap);
  }
}

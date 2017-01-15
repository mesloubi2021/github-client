package com.jraska.github.client;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.logging.CrashReporter;

import static org.mockito.Mockito.mock;

public class FakeFirebaseModule extends FirebaseModule {
  @Override FirebaseAnalytics firebaseAnalytics(Context context) {
    FirebaseAnalytics analytics = super.firebaseAnalytics(context);
    analytics.setAnalyticsCollectionEnabled(false);
    return analytics;
  }

  @Override CrashReporter firebaseCrash() {
    return mock(CrashReporter.class);
  }

  @Override Config config() {
    return mock(Config.class);
  }
}

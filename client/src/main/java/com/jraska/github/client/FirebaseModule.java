package com.jraska.github.client;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {
  @Provides @PerApp FirebaseAnalytics firebaseAnalytics(Context context) {
    return FirebaseAnalytics.getInstance(context);
  }
}

package com.jraska.github.client;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.logging.CrashReporter;
import com.jraska.github.client.logging.FirebaseCrashProxy;
import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {
  @Provides @PerApp FirebaseAnalytics firebaseAnalytics(Context context) {
    return FirebaseAnalytics.getInstance(context);
  }

  @Provides @PerApp CrashReporter firebaseCrash() {
    return new FirebaseCrashProxy();
  }
}

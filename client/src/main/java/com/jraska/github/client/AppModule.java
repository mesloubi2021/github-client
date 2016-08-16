package com.jraska.github.client;

import android.content.Context;
import android.support.annotation.NonNull;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule {
  private final GitHubClientApp _app;

  public AppModule(@NonNull GitHubClientApp app) {
    _app = app;
  }

  @Singleton @Provides Context provideContext() {
    return _app;
  }

  @Singleton @Provides Picasso picasso(Context context) {
    return Picasso.with(context);
  }
}

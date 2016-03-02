package com.jraska.github.client;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jraska.github.client.dagger.PerApp;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
  private final GitHubClientApp _app;

  public AppModule(@NonNull GitHubClientApp app) {
    _app = app;
  }

  @PerApp @Provides Context provideContext() {
    return _app;
  }

  @PerApp @Provides Picasso picasso(Context context) {
    return Picasso.with(context);
  }
}

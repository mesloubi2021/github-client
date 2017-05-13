package com.jraska.github.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

import okhttp3.HttpUrl;

final class RealWebLinkLauncher implements WebLinkLauncher {
  private final Activity activity;

  @Inject RealWebLinkLauncher(Activity activity) {
    this.activity = activity;
  }

  public void launch(HttpUrl url) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    Uri uri = Uri.parse(url.toString());
    intent.setData(uri);
    activity.startActivity(intent);
  }
}

package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraska.github.client.users.User;

import javax.inject.Inject;

public class UserOnWebStarter {
  private final Activity activity;
  private final FirebaseAnalytics analytics;

  @Inject
  public UserOnWebStarter(Activity activity, FirebaseAnalytics analytics) {
    this.activity = activity;
    this.analytics = analytics;
  }

  public void viewUserOnWeb(User user) {
    Bundle parameters = new Bundle();
    parameters.putString("login", user.login);
    analytics.logEvent("open_github", parameters);

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(user.gitHubUrl));
    activity.startActivity(intent);
  }
}

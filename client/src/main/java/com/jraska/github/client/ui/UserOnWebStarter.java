package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.jraska.github.client.users.User;

import javax.inject.Inject;

public class UserOnWebStarter {
  private final Activity activity;

  @Inject
  public UserOnWebStarter(Activity activity) {
    this.activity = activity;
  }

  public void viewUserOnWeb(User user) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(user.gitHubUrl));
    activity.startActivity(intent);
  }
}

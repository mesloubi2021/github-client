package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.jraska.github.client.users.User;

import javax.inject.Inject;

public class GitHubIconClickHandler {
  private final Activity _activity;

  @Inject
  public GitHubIconClickHandler(Activity activity) {
    _activity = activity;
  }

  public void userGitHubClicked(User user) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(user._gitHubUrl));
    _activity.startActivity(intent);
  }
}

package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.jraska.github.client.users.User;

import javax.inject.Inject;

public class GitHubIconClickHandler {
  private final Activity activity;

  @Inject
  public GitHubIconClickHandler(Activity activity) {
    this.activity = activity;
  }

  public void userGitHubClicked(User user) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(user.gitHubUrl));
    activity.startActivity(intent);
  }
}

package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity {

  static final String EXTRA_USER_KEY = "user";

  @Bind(R.id.user_detail_avatar) ImageView _avatarView;

  @Inject Picasso _picasso;
  @Inject GitHubIconClickedHandler _gitHubIconClickedHandler;

  private User _user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_detail);
    getComponent().inject(this);

    _user = getIntent().getParcelableExtra(EXTRA_USER_KEY);

    setTitle(_user._login);
    _picasso.load(_user._avatarUrl).into(_avatarView);
  }

  @OnClick(R.id.user_detail_github_fab) void gitHubFabClicked() {
    _gitHubIconClickedHandler.userGitHubClicked(_user);
  }

  public static void start(Activity inActivity, User user) {
    Intent intent = new Intent(inActivity, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_KEY, user);

    inActivity.startActivity(intent);
  }
}

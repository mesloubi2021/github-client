package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.ActivityErrorMethod;
import com.jraska.github.client.rx.ActivityNextMethod;
import com.jraska.github.client.rx.ObservableLoader;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UserDetail;
import com.jraska.github.client.users.UserStats;
import com.jraska.github.client.users.UsersRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Date;

public class UserDetailActivity extends BaseActivity {

  static final ActivityNextMethod<UserDetail, UserDetailActivity> SET_USER_METHOD = UserDetailActivity::setUserDetail;
  static final ActivityErrorMethod<UserDetailActivity> USER_LOAD_ERROR_METHOD = UserDetailActivity::onUserLoadError;

  static final DateFormat JOINED_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
  static final String EXTRA_USER_KEY = "user";

  @Bind(R.id.user_detail_avatar) ImageView _avatarView;
  @Bind(R.id.user_detail_repos_count) TextView _reposCountTextView;
  @Bind(R.id.user_detail_following_count) TextView _followingTextView;
  @Bind(R.id.user_detail_followers_count) TextView _followersTextView;
  @Bind(R.id.user_detail_joined) TextView _joinedTextView;

  @Inject Picasso _picasso;
  @Inject GitHubIconClickHandler _gitHubIconClickHandler;
  @Inject UsersRepository _usersRepository;
  @Inject ObservableLoader _observableLoader;

  private User _user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_detail);
    getComponent().inject(this);

    _user = getIntent().getParcelableExtra(EXTRA_USER_KEY);

    setTitle(_user._login);
    _picasso.load(_user._avatarUrl).into(_avatarView);

    _observableLoader.load(_usersRepository.getUserDetail(_user._login), SET_USER_METHOD, USER_LOAD_ERROR_METHOD);
  }

  @OnClick(R.id.user_detail_github_fab) void gitHubFabClicked() {
    _gitHubIconClickHandler.userGitHubClicked(_user);
  }

  void setUserDetail(UserDetail userDetail) {
    UserStats basicStats = userDetail._basicStats;

    _followersTextView.setText(String.valueOf(basicStats._followers));
    _followingTextView.setText(String.valueOf(basicStats._following));
    _reposCountTextView.setText(String.valueOf(basicStats._publicRepos));

    String joinedDateText = formatJoinedDate(basicStats._joined);
    String joinedText = getString(R.string.user_detail_joined_template, joinedDateText);
    _joinedTextView.setText(joinedText);
  }

  void onUserLoadError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
  }

  static String formatJoinedDate(Date joinedDate) {
    synchronized (JOINED_FORMAT) {
      return JOINED_FORMAT.format(joinedDate);
    }
  }

  public static void start(Activity inActivity, User user) {
    Intent intent = new Intent(inActivity, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_KEY, user);

    inActivity.startActivity(intent);
  }
}

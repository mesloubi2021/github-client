package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.AppSchedulers;
import com.jraska.github.client.users.*;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity implements UserDetailView {
  static final String EXTRA_USER_KEY = "user";

  @BindView(R.id.user_detail_avatar) ImageView avatarView;

  @Inject Picasso picasso;
  @Inject UserOnWebStarter userOnWebStarter;
  @Inject UsersRepository usersRepository;
  @Inject AppSchedulers schedulers;

  private UserStatsFragment userStatsFragment;
  private ReposFragment popularReposFragment;
  private ReposFragment contributedReposFragment;

  private UserDetailPresenter userDetailPresenter;

  public User getUser() {
    return (User) getIntent().getSerializableExtra(EXTRA_USER_KEY);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_detail);
    component().inject(this);

    userStatsFragment = (UserStatsFragment) findFragmentById(R.id.fragment_user_stats);
    popularReposFragment = (ReposFragment) findFragmentById(R.id.fragment_user_popular_repos);
    popularReposFragment.setTitle(getString(R.string.repos_popular));
    contributedReposFragment = (ReposFragment) findFragmentById(R.id.fragment_user_contributed_repos);
    contributedReposFragment.setTitle(getString(R.string.repos_contributed));

    User user = getUser();

    setTitle(user.login);
    picasso.load(user.avatarUrl).into(avatarView);

    userDetailPresenter = new UserDetailPresenter(this, usersRepository, schedulers);

    userDetailPresenter.onCreate(user.login);
  }

  @Override protected void onDestroy() {
    userDetailPresenter.onDestroy();

    super.onDestroy();
  }

  @OnClick(R.id.user_detail_github_fab) void gitHubFabClicked() {
    userDetailPresenter.onUserGitHubIconClick(getUser());
  }

  @Override
  public void setUser(UserDetail userDetail) {
    userStatsFragment.setUserStats(userDetail.basicStats);

    popularReposFragment.setRepos(userDetail.popularRepos);
    contributedReposFragment.setRepos(userDetail.contributedRepos);
  }

  @Override public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override public void viewUserOnWeb(User user) {
    userOnWebStarter.viewUserOnWeb(user);
  }

  public static void start(Activity inActivity, @NonNull User user) {
    Intent intent = new Intent(inActivity, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_KEY, user);

    inActivity.startActivity(intent);
  }
}

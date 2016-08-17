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
import com.jraska.github.client.rx.*;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UserDetail;
import com.jraska.github.client.users.UsersRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity {
  static final String EXTRA_USER_KEY = "user";

  static final SubscriberDelegateProvider<UserDetailActivity, UserDetail> USER_DELEGATE = UserDetailActivity::createDetailDelegate;

  @BindView(R.id.user_detail_avatar) ImageView avatarView;

  @Inject Picasso picasso;
  @Inject GitHubIconClickHandler gitHubIconClickHandler;
  @Inject UsersRepository usersRepository;
  @Inject ObservableLoader observableLoader;

  private UserStatsFragment userStatsFragment;
  private ReposFragment popularReposFragment;
  private ReposFragment contributedReposFragment;

  private User user;

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

    user = (User) getIntent().getSerializableExtra(EXTRA_USER_KEY);

    setTitle(user.login);
    picasso.load(user.avatarUrl).into(avatarView);

    observableLoader.load(usersRepository.getUserDetail(user.login).compose(IOPoolTransformer.get()), USER_DELEGATE);
  }

  @OnClick(R.id.user_detail_github_fab) void gitHubFabClicked() {
    gitHubIconClickHandler.userGitHubClicked(user);
  }

  void setUserDetail(UserDetail userDetail) {
    userStatsFragment.setUserStats(userDetail.basicStats);

    popularReposFragment.setRepos(userDetail.popularRepos);
    contributedReposFragment.setRepos(userDetail.contributedRepos);
  }

  void onUserLoadError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
  }

  public static void start(Activity inActivity, @NonNull User user) {
    Intent intent = new Intent(inActivity, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_KEY, user);

    inActivity.startActivity(intent);
  }

  private SubscriberDelegate<UserDetail> createDetailDelegate() {
    return new SimpleDataSubscriberDelegate<>(this::setUserDetail, this::onUserLoadError);
  }
}

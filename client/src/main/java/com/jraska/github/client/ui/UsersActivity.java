package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.airbnb.epoxy.SimpleEpoxyAdapter;
import com.jraska.github.client.Navigator;
import com.jraska.github.client.R;
import com.jraska.github.client.analytics.EventAnalytics;
import com.jraska.github.client.common.Lists;
import com.jraska.github.client.rx.AppSchedulers;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersPresenter;
import com.jraska.github.client.users.UsersRepository;
import com.jraska.github.client.users.UsersView;
import com.jraska.github.client.users.UsersViewEvents;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class UsersActivity extends BaseActivity implements UserModel.UserListener, UsersView {
  @Inject UsersRepository repository;
  @Inject AppSchedulers schedulers;
  @Inject Navigator navigator;
  @Inject EventAnalytics eventAnalytics;

  @BindView(R.id.users_refresh_swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.users_recycler) RecyclerView usersRecyclerView;

  private UsersPresenter presenter;
  private UsersViewEvents events;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    component().inject(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);

    usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    swipeRefreshLayout.setOnRefreshListener(this::refresh);

    presenter = new UsersPresenter(this, repository, schedulers, navigator, eventAnalytics);
    events = presenter;

    presenter.onCreate();
  }

  @Override protected void onDestroy() {
    presenter.onDestroy();

    super.onDestroy();
  }

  @Override
  public void onUserClicked(User user) {
    events.onUserItemClick(user);
  }

  @Override
  public void onUserGitHubIconClicked(User user) {
    events.onUserGitHubIconClick(user);
  }

  void refresh() {
    events.onRefresh();
  }

  @Override public void setUsers(List<User> users) {
    SimpleEpoxyAdapter adapter = new SimpleEpoxyAdapter();
    List<UserModel> models = Lists.transform(users,
      user -> new UserModel(user, this));

    adapter.addModels(models);
    usersRecyclerView.setAdapter(adapter);
  }

  @Override public void startDisplayProgress() {
    showProgressIndicator();
  }

  @Override public void stopDisplayProgress() {
    hideProgressIndicator();
  }

  @Override public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  void showProgressIndicator() {
    ensureProgressIndicatorVisible();

    swipeRefreshLayout.setRefreshing(true);
  }

  void hideProgressIndicator() {
    swipeRefreshLayout.setRefreshing(false);
  }

  private void ensureProgressIndicatorVisible() {
    // Workaround for start progress called before onMeasure
    // Issue: https://code.google.com/p/android/issues/detail?id=77712
    if (swipeRefreshLayout.getMeasuredHeight() == 0) {
      int circleSize = getResources().getDimensionPixelSize(R.dimen.swipe_progress_circle_diameter);
      swipeRefreshLayout.setProgressViewOffset(false, 0, circleSize);
    }
  }

  public static void start(Activity inActivity) {
    Intent intent = new Intent(inActivity, UsersActivity.class);
    inActivity.startActivity(intent);
  }
}

package com.jraska.github.client.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.jraska.github.client.R;
import com.jraska.github.client.rx.AppSchedulers;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersPresenter;
import com.jraska.github.client.users.UsersRepository;
import com.jraska.github.client.users.UsersView;
import com.jraska.github.client.users.UsersViewEvents;

import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends BaseActivity implements UsersFragment.UserListener, UsersView {
  @Inject UserOnWebStarter webStarter;
  @Inject UsersRepository repository;
  @Inject AppSchedulers schedulers;

  private UsersFragment usersFragment;
  private UsersPresenter presenter;
  private UsersViewEvents events;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    component().inject(this);

    usersFragment = (UsersFragment) findFragmentById(R.id.fragment_users);

    presenter = new UsersPresenter(this, repository, schedulers);
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
    usersFragment.setUsers(users);
  }

  @Override public void startDisplayProgress() {
    usersFragment.showProgressIndicator();
  }

  @Override public void stopDisplayProgress() {
    usersFragment.hideProgressIndicator();
  }

  @Override public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override public void startUserDetail(String login) {
    UserDetailActivity.start(this, login);
  }

  @Override public void viewUserOnWeb(String login) {
    webStarter.viewUserOnWeb(login);
  }
}

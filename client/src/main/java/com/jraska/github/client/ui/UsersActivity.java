package com.jraska.github.client.ui;

import android.os.Bundle;
import android.widget.Toast;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.ActivityErrorMethod;
import com.jraska.github.client.rx.ActivityNextMethod;
import com.jraska.github.client.rx.ObservableLoader;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserListener {

  static final ActivityNextMethod<List<User>, UsersActivity> SET_USERS_METHOD = UsersActivity::onUsersLoaded;
  static final ActivityErrorMethod<UsersActivity> ON_USERS_ERROR_METHOD = UsersActivity::onUsersError;

  @Inject UsersRepository _usersRepository;
  @Inject ObservableLoader _observableLoader;
  @Inject GitHubIconClickHandler _iconClickHandler;

  private UsersFragment _usersFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    getComponent().inject(this);

    _usersFragment = (UsersFragment) findFragmentById(R.id.fragment_users);
    _usersFragment.setUsersListener(this);

    startLoading();
  }

  void onUsersLoaded(List<User> users) {
    _usersFragment.setUsers(users);
    onLoadingFinished();
  }

  void onUsersError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

    onLoadingFinished();
  }

  void startLoading() {
    _usersFragment.showProgressIndicator();
    _observableLoader.load(_usersRepository.getUsers(0), SET_USERS_METHOD, ON_USERS_ERROR_METHOD);
  }

  void onLoadingFinished() {
    _usersFragment.hideProgressIndicator();
  }

  @Override
  public void onUserClicked(User user) {
    UserDetailActivity.start(this, user);
  }

  @Override public void onUserGitHubIconClicked(User user) {
    _iconClickHandler.userGitHubClicked(user);
  }

  void refresh() {
    // TODO: 16/04/16 Clear network cache
    startLoading();
  }
}

package com.jraska.github.client.ui;

import android.os.Bundle;
import android.widget.Toast;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.*;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserListener {

  static SubscriberDelegateProvider<UsersActivity, List<User>> USERS_DELEGATE = UsersActivity::createUsersDelegate;

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
  }

  void onUsersError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
  }

  void startLoading() {
    _observableLoader.load(_usersRepository.getUsers(0).compose(IOPoolTransformer.get()), USERS_DELEGATE);
  }

  @Override
  public void onUserClicked(User user) {
    UserDetailActivity.start(this, user);
  }

  @Override
  public void onUserGitHubIconClicked(User user) {
    _iconClickHandler.userGitHubClicked(user);
  }

  void refresh() {
    // TODO: 16/04/16 Clear network cache
    startLoading();
  }

  private SubscriberDelegate<List<User>> createUsersDelegate() {
    SimpleDataSubscriberDelegate<List<User>> simpleDelegate = new SimpleDataSubscriberDelegate<>(this::onUsersLoaded, this::onUsersError);
    return new UsersFragment.UsersFragmentAwareLoadingDelegate<>(simpleDelegate, _usersFragment);
  }
}

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

  static final SubscriberDelegateProvider<UsersActivity, List<User>> USERS_DELEGATE = UsersActivity::createUsersDelegate;

  @Inject UsersRepository usersRepository;
  @Inject ObservableLoader observableLoader;
  @Inject GitHubIconClickHandler iconClickHandler;

  private UsersFragment usersFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    getComponent().inject(this);

    usersFragment = (UsersFragment) findFragmentById(R.id.fragment_users);
    usersFragment.setUsersListener(this);

    startLoading();
  }

  void onUsersLoaded(List<User> users) {
    usersFragment.setUsers(users);
  }

  void onUsersError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
  }

  void startLoading() {
    observableLoader.load(usersRepository.getUsers(0).compose(IOPoolTransformer.get()), USERS_DELEGATE);
  }

  @Override
  public void onUserClicked(User user) {
    UserDetailActivity.start(this, user);
  }

  @Override
  public void onUserGitHubIconClicked(User user) {
    iconClickHandler.userGitHubClicked(user);
  }

  void refresh() {
    // TODO: 16/04/16 Clear network cache
    startLoading();
  }

  private SubscriberDelegate<List<User>> createUsersDelegate() {
    SimpleDataSubscriberDelegate<List<User>> simpleDelegate = new SimpleDataSubscriberDelegate<>(this::onUsersLoaded, this::onUsersError);
    return new UsersFragment.UsersFragmentAwareLoadingDelegate<>(simpleDelegate, usersFragment);
  }
}

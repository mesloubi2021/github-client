package com.jraska.github.client.ui;

import android.os.Bundle;
import android.widget.Toast;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersUseCase;
import com.jraska.github.client.users.UsersView;
import com.jraska.github.client.users.UsersViewEvents;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserListener, UsersView {
  @Inject UserOnWebStarter webStarter;
  @Inject UsersUseCase usersUseCase;

  private UsersFragment usersFragment;
  private UsersViewEvents events;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    component().inject(this);

    usersFragment = (UsersFragment) findFragmentById(R.id.fragment_users);
    usersFragment.setUsersListener(this);

    events = usersUseCase; // TODO: 18/08/16 Whole use case should not be here
    usersUseCase.onViewAttach(this);
    usersUseCase.onStart();
  }

  @Override protected void onDestroy() {
    usersUseCase.onViewDetach();
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

  @Override public void startUserDetail(User user) {
    UserDetailActivity.start(this, user);
  }

  @Override public void viewUserOnWeb(User user) {
    webStarter.viewUserOnWeb(user);
  }
}

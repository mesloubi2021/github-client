package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.Bind;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.ActivityErrorMethod;
import com.jraska.github.client.rx.ActivityNextMethod;
import com.jraska.github.client.rx.ObservableLoader;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserClickListener {

  static final ActivityNextMethod<List<User>, UsersActivity> SET_USERS_METHOD = UsersActivity::onUsersLoaded;
  static final ActivityErrorMethod<UsersActivity> ON_USERS_ERROR_METHOD = UsersActivity::onUsersError;

  @Bind(R.id.users_recycler) RecyclerView _usersRecyclerView;

  @Inject UsersRepository _usersRepository;
  @Inject ObservableLoader _observableLoader;
  @Inject UsersAdapter _usersAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    getComponent().inject(this);

    _usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    _usersRecyclerView.setAdapter(_usersAdapter);
    _usersAdapter.setUserClickListener(this);

    _observableLoader.load(_usersRepository.getUsers(0), SET_USERS_METHOD, ON_USERS_ERROR_METHOD);
  }

  void onUsersLoaded(List<User> users) {
    _usersAdapter.addUsers(users);
    _usersAdapter.notifyDataSetChanged();
  }

  void onUsersError(Throwable error) {
    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
  }

  @Override
  public void onUserClicked(User user) {
    UserDetailActivity.start(this, user);
  }
}

package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserClickListener{

  @Bind(R.id.users_recycler) RecyclerView _usersRecyclerView;

  @Inject UsersRepository _usersRepository;
  @Inject UsersAdapter _usersAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    getComponent().inject(this);

    _usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    _usersRecyclerView.setAdapter(_usersAdapter);
    _usersAdapter.setUserClickListener(this);

    _usersRepository.getUsers(0)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onUsersLoaded);
  }

  void onUsersLoaded(List<User> users) {
    _usersAdapter.addUsers(users);
    _usersAdapter.notifyDataSetChanged();
  }

  @Override
  public void onUserClicked(User user) {
    UserDetailActivity.start(this, user);
  }
}

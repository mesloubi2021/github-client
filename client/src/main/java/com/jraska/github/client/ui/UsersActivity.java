package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.GitHubClientApp;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar _toolbar;
  @Bind(R.id.users_recycler) RecyclerView _usersRecyclerView;

  @Inject UsersRepository _usersRepository;
  @Inject UsersAdapter _usersAdapter;

  GitHubClientApp getApp() {
    return (GitHubClientApp) getApplication();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);

    getApp().getComponent().inject(this);
    ButterKnife.bind(this);

    setSupportActionBar(_toolbar);
    _usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    _usersRecyclerView.setAdapter(_usersAdapter);

    _usersRepository.getUsers(0)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onUsersLoaded);
  }

  private void onUsersLoaded(List<User> users) {
    _usersAdapter.addUsers(users);
    _usersAdapter.notifyDataSetChanged();
  }
}

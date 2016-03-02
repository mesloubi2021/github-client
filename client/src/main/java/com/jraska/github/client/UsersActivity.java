package com.jraska.github.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.users.UsersApi;

import javax.inject.Inject;

public class UsersActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar _toolbar;

  @Inject UsersApi _usersApi;

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
  }
}

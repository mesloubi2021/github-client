package com.jraska.github.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar _toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    ButterKnife.bind(this);

    setSupportActionBar(_toolbar);
  }
}

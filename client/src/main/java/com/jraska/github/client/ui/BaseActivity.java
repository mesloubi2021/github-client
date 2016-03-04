package com.jraska.github.client.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.GitHubClientApp;
import com.jraska.github.client.R;

public class BaseActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) protected Toolbar _toolbar;

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    onSetContentView();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    onSetContentView();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    onSetContentView();
  }

  protected void onSetContentView() {
    ButterKnife.bind(this);
    setSupportActionBar(_toolbar);
  }

  protected GitHubClientApp getApp() {
    return (GitHubClientApp) getApplication();
  }

  protected ActivityComponent getComponent() {
    return getApp().getComponent().activityComponent(new ActivityModule(this));
  }
}

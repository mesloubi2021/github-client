package com.jraska.github.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.SimpleEpoxyAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.AppSchedulers;
import com.jraska.github.client.users.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends BaseActivity implements UserDetailView {
  static final String EXTRA_USER_KEY = "user";

  @BindView(R.id.user_detail_avatar) SimpleDraweeView avatarView;
  @BindView(R.id.user_detail_recycler) RecyclerView recyclerView;

  @Inject UserOnWebStarter userOnWebStarter;
  @Inject UsersRepository usersRepository;
  @Inject AppSchedulers schedulers;

  private UserDetailPresenter userDetailPresenter;

  public User getUser() {
    return (User) getIntent().getSerializableExtra(EXTRA_USER_KEY);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_detail);
    component().inject(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setNestedScrollingEnabled(false);

    User user = getUser();

    setTitle(user.login);
    avatarView.setImageURI(user.avatarUrl);

    userDetailPresenter = new UserDetailPresenter(this, usersRepository, schedulers);
    userDetailPresenter.onCreate(user.login);
  }

  @Override protected void onDestroy() {
    userDetailPresenter.onDestroy();

    super.onDestroy();
  }

  @OnClick(R.id.user_detail_github_fab) void gitHubFabClicked() {
    userDetailPresenter.onUserGitHubIconClick(getUser());
  }

  @Override
  public void setUser(UserDetail userDetail) {
    SimpleEpoxyAdapter adapter = new SimpleEpoxyAdapter();

    List<EpoxyModel<?>> models = new ArrayList<>();
    models.add(new UserHeaderModel(userDetail.basicStats));

    if (!userDetail.popularRepos.isEmpty()) {
      models.add(new ReposSectionModel(getString(R.string.repos_popular), userDetail.popularRepos));
    }

    if (!userDetail.contributedRepos.isEmpty()) {
      models.add(new ReposSectionModel(getString(R.string.repos_contributed), userDetail.contributedRepos));
    }

    adapter.addModels(models);

    recyclerView.setAdapter(adapter);
  }

  @Override public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override public void viewUserOnWeb(User user) {
    userOnWebStarter.viewUserOnWeb(user);
  }

  public static void start(Activity inActivity, @NonNull User user) {
    Intent intent = new Intent(inActivity, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_KEY, user);

    inActivity.startActivity(intent);
  }
}

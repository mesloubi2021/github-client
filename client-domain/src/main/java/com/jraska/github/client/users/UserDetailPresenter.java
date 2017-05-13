package com.jraska.github.client.users;

import com.jraska.github.client.Urls;
import com.jraska.github.client.Navigator;
import com.jraska.github.client.rx.AppSchedulers;

import io.reactivex.disposables.Disposable;

public class UserDetailPresenter implements UserDetailViewEvents {
  private final UserDetailView view;
  private final UsersRepository usersRepository;
  private final AppSchedulers schedulers;
  private final Navigator navigator;

  private Disposable subscription;

  public UserDetailPresenter(UserDetailView view, UsersRepository usersRepository,
                             AppSchedulers schedulers, Navigator navigator) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.schedulers = schedulers;
    this.navigator = navigator;
  }

  public void onCreate(String login) {
    subscription = usersRepository.getUserDetail(login)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.mainThread())
        .subscribe(this::onLoaded, this::onLoadError);
  }

  public void onDestroy() {
    if (subscription != null) {
      subscription.dispose();
    }
  }

  void onLoaded(UserDetail userDetail) {
    view.setUser(userDetail);
  }

  void onLoadError(Throwable throwable) {
    view.showMessage(throwable.toString());
  }

  @Override public void onUserGitHubIconClick(String login) {
    // TODO(josef):
//    Bundle parameters = new Bundle();
//    parameters.putString("login", login);
//    analytics.logEvent("open_github", parameters);

    navigator.launchOnWeb(Urls.user(login));
  }
}

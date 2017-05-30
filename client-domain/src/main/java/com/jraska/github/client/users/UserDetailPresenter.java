package com.jraska.github.client.users;

import com.jraska.github.client.Urls;
import com.jraska.github.client.Navigator;
import com.jraska.github.client.analytics.AnalyticsEvent;
import com.jraska.github.client.analytics.EventAnalytics;
import com.jraska.github.client.rx.AppSchedulers;

import io.reactivex.disposables.Disposable;

public class UserDetailPresenter implements UserDetailViewEvents {
  private final UserDetailView view;
  private final UsersRepository usersRepository;
  private final AppSchedulers schedulers;
  private final Navigator navigator;
  private final EventAnalytics eventAnalytics;

  private Disposable subscription;

  public UserDetailPresenter(UserDetailView view, UsersRepository usersRepository,
                             AppSchedulers schedulers, Navigator navigator, EventAnalytics eventAnalytics) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.schedulers = schedulers;
    this.navigator = navigator;
    this.eventAnalytics = eventAnalytics;
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
    AnalyticsEvent event = AnalyticsEvent.builder("open_github_from_detail")
        .addProperty("login", login)
        .build();

    eventAnalytics.report(event);

    navigator.launchOnWeb(Urls.user(login));
  }
}

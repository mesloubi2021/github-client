package com.jraska.github.client.users;

import com.jraska.github.client.rx.AppSchedulers;
import io.reactivex.disposables.Disposable;

public class UserDetailPresenter implements UserDetailViewEvents {
  private final UserDetailView view;
  private final UsersRepository usersRepository;
  private final AppSchedulers schedulers;
  private Disposable subscription;

  public UserDetailPresenter(UserDetailView view, UsersRepository usersRepository,
                             AppSchedulers schedulers) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.schedulers = schedulers;
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
    view.viewUserOnWeb(login);
  }
}

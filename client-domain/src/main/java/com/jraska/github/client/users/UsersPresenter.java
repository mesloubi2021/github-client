package com.jraska.github.client.users;

import com.jraska.github.client.common.UseCase;
import com.jraska.github.client.rx.AppSchedulers;
import rx.Subscription;

import java.util.List;

public class UsersPresenter implements UseCase, UsersViewEvents {
  private final UsersView view;
  private final UsersRepository usersRepository;
  private final AppSchedulers schedulers;
  private Subscription subscription;

  public UsersPresenter(UsersView view, UsersRepository usersRepository,
                        AppSchedulers schedulers) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.schedulers = schedulers;
  }

  public void onCreate() {
    subscription = usersRepository.getUsers(0)
        .compose(schedulers.ioLoadTransformer())
        .doOnSubscribe(view::startDisplayProgress)
        .doOnUnsubscribe(view::stopDisplayProgress)
        .subscribe(this::onLoaded, this::onLoadError);
  }

  public void onDestroy() {
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  void onLoaded(List<User> users) {
    view.setUsers(users);
  }

  void onLoadError(Throwable throwable) {
    view.showMessage(throwable.toString());
  }

  @Override public void onUserItemClick(User user) {
    view.startUserDetail(user);
  }

  @Override public void onUserGitHubIconClick(User user) {
    view.viewUserOnWeb(user);
  }

  @Override public void onRefresh() {
    onCreate();
  }
}

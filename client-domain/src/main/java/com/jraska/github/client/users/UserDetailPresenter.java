package com.jraska.github.client.users;

import com.jraska.github.client.rx.AppSchedulers;
import rx.Subscription;

public class UserDetailPresenter implements UserDetailViewEvents {
  private final UserDetailView view;
  private final UsersRepository usersRepository;
  private final AppSchedulers schedulers;
  private Subscription subscription;

  public UserDetailPresenter(UserDetailView view, UsersRepository usersRepository,
                             AppSchedulers schedulers) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.schedulers = schedulers;
  }

  public void onCreate(String login) {
    subscription = usersRepository.getUserDetail(login)
        .compose(schedulers.ioLoadTransformer())
        .subscribe(this::onLoaded, this::onLoadError);
  }

  public void onDestroy() {
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  void onLoaded(UserDetail userDetail) {
    view.setUser(userDetail);
  }

  void onLoadError(Throwable throwable) {
    view.showMessage(throwable.toString());
  }

  @Override public void   onUserGitHubIconClick(User user) {
    view.viewUserOnWeb(user);
  }
}

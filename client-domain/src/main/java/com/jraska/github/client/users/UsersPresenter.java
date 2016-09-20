package com.jraska.github.client.users;

import com.jraska.github.client.common.UseCase;
import com.jraska.github.client.rx.DataTransformerFactory;

import java.util.Collections;
import java.util.List;

public class UsersPresenter implements UseCase, UsersViewEvents {
  private final UsersView view;
  private final UsersRepository usersRepository;
  private final DataTransformerFactory transformerFactory;

  public UsersPresenter(UsersView view, UsersRepository usersRepository,
                        DataTransformerFactory transformerFactory) {
    this.view = view;
    this.usersRepository = usersRepository;
    this.transformerFactory = transformerFactory;
  }

  public void onCreate() {
    usersRepository.getUsers(0)
        .compose(transformerFactory.get())
        .doOnSubscribe(view::startDisplayProgress)
        .doOnTerminate(view::stopDisplayProgress)
        .subscribe(this::onLoaded, this::onLoadError);
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

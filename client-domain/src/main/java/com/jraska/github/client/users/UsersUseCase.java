package com.jraska.github.client.users;

import com.jraska.github.client.common.UseCase;
import com.jraska.github.client.rx.DataTransformerFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class UsersUseCase implements UseCase, UsersViewEvents {
  private UsersView view;

  List<User> loadedUsers;

  private final UsersRepository usersRepository;
  private final DataTransformerFactory transformerFactory;

  @Inject
  public UsersUseCase(UsersRepository usersRepository,
                      DataTransformerFactory transformerFactory) {
    this.usersRepository = usersRepository;
    this.transformerFactory = transformerFactory;
  }

  public void onStart() {
    loadedUsers = null;
    usersRepository.getUsers(0)
        .compose(transformerFactory.get())
        .doOnSubscribe(() -> view.startDisplayProgress())
        .doOnTerminate(() -> view.stopDisplayProgress())
        .subscribe(this::onLoaded, this::onLoadError);
  }

  public void onViewAttach(UsersView usersView) {
    view = usersView;
    updateUsers();
  }

  public void onViewDetach() {
    view = null;
  }

  void onLoaded(List<User> users) {
    loadedUsers = users;
    updateUsers();
  }

  void onLoadError(Throwable throwable) {
    loadedUsers = null;
    if (view != null) {
      view.showMessage(throwable.toString());
    }
  }

  private void updateUsers() {
    if (view != null) {
      view.setUsers(loadedUsers());
    }
  }

  List<User> loadedUsers() {
    if (loadedUsers == null) {
      return Collections.emptyList();
    } else {
      return loadedUsers;
    }
  }

  @Override public void onUserItemClick(User user) {
    view.startUserDetail(user);
  }

  @Override public void onUserGitHubIconClick(User user) {
    view.viewUserOnWeb(user);
  }

  @Override public void onRefresh() {
    onStart();
  }
}

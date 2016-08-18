package com.jraska.github.client.users;

import java.util.List;

public interface UsersView {
  void setUsers(List<User> users);

  void startDisplayProgress();

  void stopDisplayProgress();

  void showMessage(String message);

  void startUserDetail(User user); // TODO: 18/08/16 Rmove this from View interface

  void viewUserOnWeb(User user); // TODO: 18/08/16 Rmove this from View interface
}

package com.jraska.github.client.users;

import java.util.List;

public interface UsersView {
  void setUsers(List<User> users);

  void startDisplayProgress();

  void stopDisplayProgress();

  void showMessage(String message);

  void startUserDetail(String login); // TODO: 18/08/16 Remove this from View interface

  void viewUserOnWeb(String logim); // TODO: 18/08/16 Remove this from View interface
}

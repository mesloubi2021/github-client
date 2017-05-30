package com.jraska.github.client.users;

import java.util.List;

public interface UsersView {
  void setUsers(List<User> users);

  void startDisplayProgress();

  void stopDisplayProgress();

  void showMessage(String message);
}

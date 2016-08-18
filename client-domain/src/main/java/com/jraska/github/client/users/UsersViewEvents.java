package com.jraska.github.client.users;

public interface UsersViewEvents {
  void onUserItemClick(User user);

  void onUserGitHubIconClick(User user);

  void onRefresh();
}

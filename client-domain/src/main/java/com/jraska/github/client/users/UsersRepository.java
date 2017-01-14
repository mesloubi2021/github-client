package com.jraska.github.client.users;

import rx.Single;

import java.util.List;

public interface UsersRepository {
  Single<List<User>> getUsers(int since);

  Single<UserDetail> getUserDetail(String login);
}

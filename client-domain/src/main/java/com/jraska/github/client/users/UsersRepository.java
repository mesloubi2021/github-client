package com.jraska.github.client.users;

import io.reactivex.Single;

import java.util.List;

public interface UsersRepository {
  Single<List<User>> getUsers(int since);

  Single<UserDetail> getUserDetail(String login);
}

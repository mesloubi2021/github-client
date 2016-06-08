package com.jraska.github.client.users;

import rx.Observable;

import java.util.List;

public interface UsersRepository {
  Observable<List<User>> getUsers(int since);

  Observable<UserDetail> getUserDetail(String login);
}

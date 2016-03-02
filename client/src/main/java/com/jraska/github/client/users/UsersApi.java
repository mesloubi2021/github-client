package com.jraska.github.client.users;

import rx.Observable;

import java.util.List;

public interface UsersApi {
  Observable<List<User>> getUsers(int since);
}

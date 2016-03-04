package com.jraska.github.client.ui;

import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(UsersActivity usersActivity);

  void inject(UserDetailActivity userDetailActivity);
}

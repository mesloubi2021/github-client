package com.jraska.github.client;

import com.jraska.github.client.dagger.PerApp;
import com.jraska.github.client.ui.UsersActivity;
import com.jraska.github.client.users.UsersModule;
import dagger.Component;

@PerApp
@Component(modules = {
    UsersModule.class,
    SystemServicesModule.class,
    AppModule.class
})
public interface AppComponent {
  void inject(UsersActivity usersActivity);
}

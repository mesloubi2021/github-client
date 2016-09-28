package com.jraska.github.client;

import com.jraska.github.client.data.users.UsersModule;
import com.jraska.github.client.http.HttpModule;
import com.jraska.github.client.ui.ActivityComponent;
import com.jraska.github.client.ui.ActivityModule;
import com.jraska.github.client.ui.UsersFragment;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
    HttpModule.class,
    UsersModule.class,
    SystemServicesModule.class,
    AppModule.class
})
public interface AppComponent {
  ActivityComponent activityComponent(ActivityModule activityModule);

  void inject(UsersFragment usersFragment);
}

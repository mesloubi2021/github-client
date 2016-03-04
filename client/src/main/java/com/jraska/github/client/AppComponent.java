package com.jraska.github.client;

import com.jraska.github.client.dagger.PerApp;
import com.jraska.github.client.ui.ActivityComponent;
import com.jraska.github.client.ui.ActivityModule;
import com.jraska.github.client.users.UsersModule;
import dagger.Component;

@PerApp
@Component(modules = {
    UsersModule.class,
    SystemServicesModule.class,
    AppModule.class
})
public interface AppComponent {
  ActivityComponent activityComponent(ActivityModule activityModule);
}

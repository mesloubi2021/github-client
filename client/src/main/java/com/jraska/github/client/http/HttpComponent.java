package com.jraska.github.client.http;

import dagger.Component;
import retrofit2.Retrofit;

@Http
@Component(modules = {HttpDependenciesModule.class, HttpModule.class})
public interface HttpComponent {
  Retrofit retrofit();
}

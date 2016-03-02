package com.jraska.github.client.users;

import com.jraska.github.client.dagger.PerApp;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class UsersModule {
  @Provides @PerApp GitHubUsersApi provideGitHubUsersApi() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(GitHubUsersApi.class);
  }

  @Provides @PerApp UsersRepository provideUsersApi(GitHubUsersApi gitHubUsersApi) {
    return new UsersRepositoryImpl(gitHubUsersApi);
  }
}

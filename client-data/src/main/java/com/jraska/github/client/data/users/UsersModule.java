package com.jraska.github.client.data.users;

import com.jraska.github.client.PerApp;
import com.jraska.github.client.users.UsersRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class UsersModule {
  @Provides
  @PerApp
  public static UsersRepository provideUsersRepository(Retrofit retrofit) {
    GitHubUsersApi usersApi = retrofit.create(GitHubUsersApi.class);
    GitHubUserDetailApi detailApi = retrofit.create(GitHubUserDetailApi.class);

    return new GitHubApiUsersRepository(usersApi, detailApi);
  }
}

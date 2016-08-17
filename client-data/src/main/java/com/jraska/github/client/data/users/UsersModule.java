package com.jraska.github.client.data.users;

import com.jraska.github.client.common.AppBuildConfig;
import com.jraska.github.client.users.UsersRepository;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public final class UsersModule {
  @Provides @Singleton UsersRepository provideUsersRepository(Retrofit retrofit) {
    GitHubUsersApi usersApi = retrofit.create(GitHubUsersApi.class);
    GitHubUserDetailApi detailApi = retrofit.create(GitHubUserDetailApi.class);

    return new GitHubApiUsersRepository(usersApi, detailApi);
  }

  @Provides @Singleton Retrofit provideRetrofit(OkHttpClient okHttpClient, AppBuildConfig config) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .validateEagerly(config.debug)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit;
  }
}

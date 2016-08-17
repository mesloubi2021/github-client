package com.jraska.github.client.data.users;

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
  @Provides @Singleton UsersRepository provideUsersRepository(GitHubUsersApi gitHubUsersApi,
                                                              GitHubUserDetailApi detailApi) {
    return new GitHubApiUsersRepository(gitHubUsersApi, detailApi);
  }

  @Provides @Singleton GitHubUsersApi provideGitHubUsersApi(Retrofit retrofit) {
    return retrofit.create(GitHubUsersApi.class);
  }

  @Provides @Singleton GitHubUserDetailApi provideGitHubUserDetailApi(Retrofit retrofit) {
    return retrofit.create(GitHubUserDetailApi.class);
  }

  @Provides @Singleton Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .validateEagerly(true) // TODO: 17/08/16 AppConfig
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit;
  }
}

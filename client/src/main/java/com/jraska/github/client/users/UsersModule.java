package com.jraska.github.client.users;

import com.jraska.github.client.BuildConfig;
import com.jraska.github.client.dagger.PerApp;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class UsersModule {
  @Provides @PerApp UsersRepository provideUsersRepository(GitHubUsersApi gitHubUsersApi,
                                                           GitHubUserDetailApi detailApi) {
    return new GitHubApiUsersRepository(gitHubUsersApi, detailApi);
  }

  @Provides @PerApp GitHubUsersApi provideGitHubUsersApi(Retrofit retrofit) {
    return retrofit.create(GitHubUsersApi.class);
  }

  @Provides @PerApp GitHubUserDetailApi provideGitHubUserDetailApi(Retrofit retrofit) {
    return retrofit.create(GitHubUserDetailApi.class);
  }

  @Provides @PerApp Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .validateEagerly(BuildConfig.DEBUG)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit;
  }
}

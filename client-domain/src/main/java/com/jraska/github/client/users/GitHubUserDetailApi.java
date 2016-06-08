package com.jraska.github.client.users;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

interface GitHubUserDetailApi {
  @GET("/users/{login}")
  Observable<GitHubUserDetail> getUserDetail(@Path("login") String login);

  @GET("/users/{login}/repos?type=all")
  Observable<List<GitHubRepo>> getRepos(@Path("login") String login);
}

package com.jraska.github.client.data.users;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

import java.util.List;

interface GitHubUsersApi {
  @GET("/users") Single<List<GitHubUser>> getUsers(@Query("since") int since);
}

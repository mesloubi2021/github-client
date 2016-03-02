package com.jraska.github.client.users;

import android.database.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

interface GitHubUsersApi {
  @GET("/users") Observable<List<GitHubUser>> getUsers(@Query("since") int since);
}

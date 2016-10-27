package com.jraska.github.client;

import com.jraska.github.client.http.FakeHttpComponent;
import com.jraska.github.client.http.HttpComponent;

public final class TestGitHubClientApp extends GitHubClientApp {
  @Override protected HttpComponent httpComponent() {
    return FakeHttpComponent.create();
  }
}

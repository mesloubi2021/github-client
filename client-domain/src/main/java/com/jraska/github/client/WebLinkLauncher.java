package com.jraska.github.client;

import okhttp3.HttpUrl;

public interface WebLinkLauncher {
  void launch(HttpUrl url);
}

package com.jraska.github.client;

import okhttp3.HttpUrl;

public interface DeepLinkLauncher {
  void launch(HttpUrl deepLink);
}

package com.jraska.github.client;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class FirebaseConfigProxy implements Config {
  private final FirebaseRemoteConfig config;

  FirebaseConfigProxy(FirebaseRemoteConfig config) {
    this.config = config;
  }

  @Override public boolean getBoolean(String key) {
    return config.getBoolean(key);
  }

  void fetch() {
    config.fetch().addOnCompleteListener(task -> config.activateFetched());
  }
}

package com.jraska.github.client;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class FirebaseConfigProxy implements Config {
  private final long TWO_MINUTES = 2 * 60;

  private final FirebaseRemoteConfig config;

  FirebaseConfigProxy(FirebaseRemoteConfig config) {
    this.config = config;
  }

  @Override public boolean getBoolean(String key) {
    return config.getBoolean(key);
  }

  void fetch() {
    config.fetch(TWO_MINUTES).addOnCompleteListener(task -> config.activateFetched());
  }
}

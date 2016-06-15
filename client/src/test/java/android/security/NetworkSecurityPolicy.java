package android.security;

import android.annotation.TargetApi;
import android.os.Build;

// https://github.com/square/okhttp/issues/2533
public class NetworkSecurityPolicy {
  private static final NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();

  @TargetApi(Build.VERSION_CODES.M)
  public static NetworkSecurityPolicy getInstance() {
    return INSTANCE;
  }

  public boolean isCleartextTrafficPermitted() {
    return true;
  }
}

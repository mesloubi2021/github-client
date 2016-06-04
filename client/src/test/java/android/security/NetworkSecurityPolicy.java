package android.security;

// https://github.com/square/okhttp/issues/2533
public class NetworkSecurityPolicy {
  private static final NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();

  public static NetworkSecurityPolicy getInstance() {
    return INSTANCE;
  }

  public boolean isCleartextTrafficPermitted() {
    return true;
  }
}

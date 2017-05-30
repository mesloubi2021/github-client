package com.jraska.github.client;

import org.junit.Test;

import okhttp3.HttpUrl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeepLinkNavigatorTest {
  @Test
  public void whenStartUserDetail_thenCorrectDeepLinkLaunched() {
    DeepLinkLauncher deepLinkLauncher = mock(DeepLinkLauncher.class);
    WebLinkLauncher webLinkLauncher = mock(WebLinkLauncher.class);
    DeepLinkNavigator navigator = new DeepLinkNavigator(deepLinkLauncher, webLinkLauncher);

    navigator.startUserDetail("johny");

    verify(deepLinkLauncher).launch(HttpUrl.parse("https://github.com/johny"));
  }
}
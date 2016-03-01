package com.jraska.github.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UsersActivityTest {
  @Test
  public void whenCreated_thenNoException() throws Exception {
    Robolectric.setupActivity(UsersActivity.class);
  }
}
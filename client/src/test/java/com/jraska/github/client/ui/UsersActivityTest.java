package com.jraska.github.client.ui;

import com.jraska.github.client.BuildConfig;
import com.jraska.github.client.ui.UsersActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UsersActivityTest {
  @Test
  public void whenCreated_thenUsersApiInjected() throws Exception {
    UsersActivity usersActivity = Robolectric.setupActivity(UsersActivity.class);

    assertThat(usersActivity._usersApi).isNotNull();
  }
}
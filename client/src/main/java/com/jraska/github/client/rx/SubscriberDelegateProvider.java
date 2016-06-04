package com.jraska.github.client.rx;

import android.support.v4.app.FragmentActivity;

public interface SubscriberDelegateProvider<TActivity extends FragmentActivity, R> {
  SubscriberDelegate<R> delegate(TActivity activity);
}

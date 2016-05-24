package com.jraska.github.client.rx;

import android.support.v4.app.FragmentActivity;

public interface ResultDelegateProvider<TActivity extends FragmentActivity, R> {
  ResultDelegate<R> delegate(TActivity activity);
}

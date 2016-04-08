package com.jraska.github.client.rx;

public interface ActivityNextMethod<TResult, TActivity> {
  void call(TActivity activity, TResult result);
}

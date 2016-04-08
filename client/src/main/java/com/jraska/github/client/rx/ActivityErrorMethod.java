package com.jraska.github.client.rx;

public interface ActivityErrorMethod<TActivity> {
  void call(TActivity activity, Throwable error);
}

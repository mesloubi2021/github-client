package com.jraska.github.client.rx;

import rx.Observer;

public interface ResultDelegate<R> extends Observer<R> {
  void onStart();

  void onNext(R result);

  void onError(Throwable error);

  void onCompleted();
}

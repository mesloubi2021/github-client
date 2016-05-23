package com.jraska.github.client.rx;

import rx.functions.Action1;

public class SimpleDataResultDelegate<R> implements ResultDelegate<R> {
  private final Action1<R> nextCall;
  private final Action1<Throwable> errorCall;

  public SimpleDataResultDelegate(Action1<R> nextCall, Action1<Throwable> errorCall) {
    this.nextCall = nextCall;
    this.errorCall = errorCall;
  }

  @Override
  public void onStart() {
  }

  @Override
  public void onNext(R result) {
    nextCall.call(result);
  }

  @Override
  public void onError(Throwable error) {
    errorCall.call(error);
  }

  @Override public void onCompleted() {
  }
}

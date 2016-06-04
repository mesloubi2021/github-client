package com.jraska.github.client.rx;

import rx.functions.Action1;

public class SimpleDataSubscriberDelegate<R> implements SubscriberDelegate<R> {
  private final Action1<R> _nextCall;
  private final Action1<Throwable> _errorCall;

  public SimpleDataSubscriberDelegate(Action1<R> nextCall, Action1<Throwable> errorCall) {
    _nextCall = nextCall;
    _errorCall = errorCall;
  }

  @Override
  public void onStart() {
  }

  @Override
  public void onNext(R result) {
    _nextCall.call(result);
  }

  @Override
  public void onError(Throwable error) {
    _errorCall.call(error);
  }

  @Override public void onCompleted() {
  }
}

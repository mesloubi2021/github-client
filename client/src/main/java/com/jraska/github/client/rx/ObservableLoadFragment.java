package com.jraska.github.client.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

public class ObservableLoadFragment<A extends FragmentActivity, R> extends Fragment {
  public static final String TAG = ObservableLoadFragment.class.getSimpleName();

  private boolean validInstance;

  private final Observable<R> observable;
  private final SubscriberDelegateProvider<A, R> subscriberDelegateProvider;

  private Result<A> result;
  private Subscriber<R> subscriber;
  private boolean deliverRequested;

  public ObservableLoadFragment() {
    this(null, null);

    validInstance = false;
  }

  private ObservableLoadFragment(Observable<R> observable,
                                 SubscriberDelegateProvider<A, R> subscriberDelegateProvider) {
    validInstance = true;

    setRetainInstance(true);

    this.observable = observable;
    this.subscriberDelegateProvider = subscriberDelegateProvider;
  }

  public boolean isValid() {
    return validInstance;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (observable == null) {
      Timber.d("Detaching useless fragment %s created by system", this);
      detachSelf();
      return;
    }

    load();
  }

  @Override
  public void onResume() {
    super.onResume();

    deliverResult();
  }

  @Override
  public void onDestroy() {
    if (subscriber != null && !subscriber.isUnsubscribed()) {
      subscriber.unsubscribe();
    }

    super.onDestroy();
  }

  @SuppressWarnings("unchecked") SubscriberDelegate<R> delegate() {
    return subscriberDelegateProvider.delegate((A) getActivity());
  }

  void requestDeliver() {
    deliverRequested = true;
    delegate().onStart();
    if (isResumed()) {
      deliverResult();
    }
  }

  private void load() {
    delegate().onStart();
    Timber.d("Subscribing");
    subscriber = new LoadingSubscriber();

    observable.subscribe(subscriber);
  }

  private void onResult(Result<A> newResult) {
    deliverRequested = true;
    result = newResult;

    if (isResumed()) {
      deliverResult();
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverResult() {
    if (!deliverRequested || result == null) {
      return;
    }

    deliverRequested = false;
    Timber.d("Delivering %s to %s", result.getClass().getSimpleName(), getActivity().getClass().getSimpleName());
    result.deliver((A) getActivity());
  }

  void detachSelf() {
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();

    validInstance = false;
  }


  private interface Result<A extends FragmentActivity> {
    void deliver(A activity);
  }

  private class SuccessResult implements Result<A> {
    private final R result;

    public SuccessResult(R result) {
      this.result = result;
    }

    @Override
    public void deliver(A activity) {
      delegate().onNext(result);
    }
  }

  private class ErrorResult implements Result<A> {
    private final Throwable error;

    public ErrorResult(Throwable error) {
      this.error = error;
    }

    @Override
    public void deliver(A activity) {
      delegate().onError(error);
    }
  }

  static <A extends FragmentActivity, R> ObservableLoadFragment<A, R> newInstance(
      Observable<R> observable, SubscriberDelegateProvider<A, R> subscriberDelegateProvider) {
    if (observable == null) {
      throw new IllegalArgumentException("observable cannot be null");
    }

    if (subscriberDelegateProvider == null) {
      throw new IllegalArgumentException("resultDelegateProvider cannot be null");
    }

    return new ObservableLoadFragment<>(observable, subscriberDelegateProvider);
  }

  class LoadingSubscriber extends Subscriber<R> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
      onResult(new ErrorResult(e));
    }

    @Override
    public void onNext(R result) {
      onResult(new SuccessResult(result));
    }
  }
}

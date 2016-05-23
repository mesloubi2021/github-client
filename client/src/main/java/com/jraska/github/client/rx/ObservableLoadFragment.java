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

  private boolean _validInstance;

  private final Observable<R> _observable;
  private final ResultDelegateProvider<A, R> _resultDelegateProvider;

  private Result<A> _result;
  private Subscriber<R> _subscriber;
  private boolean _deliverRequested;

  public ObservableLoadFragment() {
    this(null, null);

    _validInstance = false;
  }

  private ObservableLoadFragment(Observable<R> observable,
                                 ResultDelegateProvider<A, R> resultDelegateProvider) {
    _validInstance = true;

    setRetainInstance(true);

    _observable = observable;
    _resultDelegateProvider = resultDelegateProvider;
  }

  public boolean isValid() {
    return _validInstance;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (_observable == null) {
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
    _subscriber.unsubscribe();

    super.onDestroy();
  }

  @SuppressWarnings("unchecked") ResultDelegate<R> delegate() {
    return _resultDelegateProvider.delegate((A) getActivity());
  }

  void requestDeliver() {
    _deliverRequested = true;
    delegate().onStart();
    if (isResumed()) {
      deliverResult();
    }
  }

  private void load() {
    requestDeliver();
    Timber.d("Subscribing");
    _subscriber = new LoadingSubscriber();

    _observable.subscribe(_subscriber);
  }

  private void onResult(Result<A> result) {
    _result = result;

    if (isResumed()) {
      deliverResult();
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverResult() {
    if (!_deliverRequested || _result == null) {
      return;
    }

    _deliverRequested = false;
    Timber.d("Delivering %s to %s", _result.getClass().getSimpleName(), getActivity().getClass().getSimpleName());
    _result.deliver((A) getActivity());
  }

  void detachSelf() {
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();

    _validInstance = false;
  }


  private interface Result<A extends FragmentActivity> {
    void deliver(A activity);
  }

  private class SuccessResult implements Result<A> {
    private final R _result;

    public SuccessResult(R result) {
      _result = result;
    }

    @Override
    public void deliver(A activity) {
      delegate().onNext(_result);
    }
  }

  private class ErrorResult implements Result<A> {
    private final Throwable _error;

    public ErrorResult(Throwable _error) {
      this._error = _error;
    }

    @Override
    public void deliver(A activity) {
      delegate().onError(_error);
    }
  }

  static <A extends FragmentActivity, R> ObservableLoadFragment<A, R> newInstance(
      Observable<R> observable, ResultDelegateProvider<A, R> resultDelegateProvider) {
    if (observable == null) {
      throw new IllegalArgumentException("observable cannot be null");
    }

    if (resultDelegateProvider == null) {
      throw new IllegalArgumentException("resultDelegateProvider cannot be null");
    }

    return new ObservableLoadFragment<>(observable, resultDelegateProvider);
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

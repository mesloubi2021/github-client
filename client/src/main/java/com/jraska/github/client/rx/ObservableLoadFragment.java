package com.jraska.github.client.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;
import timber.log.Timber;

public class ObservableLoadFragment<A extends FragmentActivity, R> extends Fragment {
  public static final String TAG = ObservableLoadFragment.class.getSimpleName();

  private boolean _validInstance;

  private final Observable<R> _observable;
  private final Action2<A, R> _resultCall;
  private final Action2<A, Throwable> _errorCall;

  private Result<A> _result;
  private Subscriber<R> _subscriber;
  private boolean _deliverRequested;

  public ObservableLoadFragment() {
    this(null, null, null);

    _validInstance = false;
  }

  private ObservableLoadFragment(Observable<R> observable,
                                 Action2<A, R> resultCall,
                                 Action2<A, Throwable> errorCall) {
    _validInstance = true;

    setRetainInstance(true);

    _observable = observable;
    _resultCall = resultCall;
    _errorCall = errorCall;
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

  void requestDeliver() {
    _deliverRequested = true;
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
    if (!_deliverRequested) {
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

  private static class SuccessResult<A extends FragmentActivity, R> implements Result<A> {
    private final R _result;
    private final Action2<A, R> _resultCall;

    private SuccessResult(R result, Action2<A, R> resultCall) {
      _result = result;
      _resultCall = resultCall;
    }

    @Override public void deliver(A activity) {
      _resultCall.call(activity, _result);
    }
  }

  private static class ErrorResult<A extends FragmentActivity> implements Result<A> {
    private final Throwable _error;
    private final Action2<A, Throwable> _errorCall;

    public ErrorResult(Throwable error, Action2<A, Throwable> errorCall) {
      _error = error;
      _errorCall = errorCall;
    }

    @Override public void deliver(A activity) {
      _errorCall.call(activity, _error);
    }
  }

  static <A extends FragmentActivity, R> ObservableLoadFragment<A, R> newInstance(
      Observable<R> observable, Action2<A, R> resultCall, Action2<A, Throwable> errorCall) {
    if (observable == null) {
      throw new IllegalArgumentException("observable cannot be null");
    }

    if (resultCall == null) {
      throw new IllegalArgumentException("resultCall cannot be null");
    }

    if (errorCall == null) {
      throw new IllegalArgumentException("errorCall cannot be null");
    }

    ObservableLoadFragment<A, R> fragment = new ObservableLoadFragment<>(observable, resultCall, errorCall);

    return fragment;
  }

  class LoadingSubscriber extends Subscriber<R> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      onResult(new ErrorResult<>(e, _errorCall));
    }

    @Override public void onNext(R result) {
      onResult(new SuccessResult<>(result, _resultCall));
    }
  }
}

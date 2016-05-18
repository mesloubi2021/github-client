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

  private final Observable<R> _observable;
  private final Action2<A, R> _resultMethod;
  private final Action2<A, Throwable> _errorMethod;
  private boolean _validInstance;

  private Throwable _errorToDeliver;
  private R _resultToDeliver;
  private Subscriber<R> _subscriber;

  public ObservableLoadFragment() {
    this(null, null, null);

    _validInstance = false;
  }

  private ObservableLoadFragment(Observable<R> observable,
                                 Action2<A, R> resultMethod,
                                 Action2<A, Throwable> errorMethod) {
    _validInstance = true;

    setRetainInstance(true);

    _observable = observable;
    _resultMethod = resultMethod;
    _errorMethod = errorMethod;
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

    deliverResultGotOnDetached();
  }

  private void load() {
    Timber.d("Subscribing");
    _subscriber = new LoadingSubscriber();

    _observable.subscribe(_subscriber);
  }

  private void onResult(R result) {
    if (getActivity() != null) {
      deliverResult(result);
    } else {
      _resultToDeliver = result;
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverResult(R result) {
    Timber.d("Delivering to %s", getActivity());
    _resultMethod.call((A) getActivity(), result);
    detachSelf();
  }

  private void onErrorInLoading(Throwable error) {
    if (getActivity() != null) {
      deliverError(error);
    } else {
      _errorToDeliver = error;
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverError(Throwable error) {
    _errorMethod.call((A) getActivity(), error);
    detachSelf();
  }

  void detachSelf() {
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .remove(this)
        .commit();

    _validInstance = false;
  }

  private void deliverResultGotOnDetached() {
    if (_resultToDeliver != null) {
      deliverResult(_resultToDeliver);
    }

    if (_errorToDeliver != null) {
      deliverError(_errorToDeliver);
    }
  }

  static <A extends FragmentActivity, R> ObservableLoadFragment<A, R> newInstance(
      Observable<R> observable, Action2<A, R> resultCall, Action2<A, Throwable> errorMethod) {
    ObservableLoadFragment<A, R> fragmentProxy = new ObservableLoadFragment<>(observable, resultCall, errorMethod);
    return fragmentProxy;
  }

  class LoadingSubscriber extends Subscriber<R> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      onErrorInLoading(e);
    }

    @Override public void onNext(R result) {
      onResult(result);
    }
  }
}

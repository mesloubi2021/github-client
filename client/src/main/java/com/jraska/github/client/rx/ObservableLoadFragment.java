package com.jraska.github.client.rx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ObservableLoadFragment<TResult, TActivity extends Activity> extends Fragment {
  public static final String TAG = "LoadingFragment";

  private final Observable<TResult> _observable;
  private final ActivityNextMethod<TResult, TActivity> _resultMethod;
  private final ActivityErrorMethod<TActivity> _errorMethod;
  private boolean _validInstance;

  private Throwable errorToDeliver;
  private TResult resultToDeliver;

  public ObservableLoadFragment() {
    this(null, null, null);
  }

  public boolean isValid() {
    return _validInstance;
  }

  private ObservableLoadFragment(Observable<TResult> observable,
                                 ActivityNextMethod<TResult, TActivity> resultMethod,
                                 ActivityErrorMethod<TActivity> errorMethod) {
    _validInstance = true;

    setRetainInstance(true);

    _observable = observable;
    _resultMethod = resultMethod;
    _errorMethod = errorMethod;
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
    _observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new LoadingSubscriber());
  }

  private void onResult(TResult result) {
    if (getActivity() != null) {
      deliverResult(result);
    } else {
      resultToDeliver = result;
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverResult(TResult result) {
    Timber.d("Delivering to %s", getActivity());
    _resultMethod.call((TActivity) getActivity(), result);
    detachSelf();
  }

  private void onErrorInLoading(Throwable error) {
    if (getActivity() != null) {
      deliverError(error);
    } else {
      errorToDeliver = error;
    }
  }

  @SuppressWarnings("unchecked")
  private void deliverError(Throwable error) {
    _errorMethod.call((TActivity) getActivity(), error);
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
    if (resultToDeliver != null) {
      deliverResult(resultToDeliver);
    }

    if (errorToDeliver != null) {
      deliverError(errorToDeliver);
    }
  }

  static <T, A extends Activity> ObservableLoadFragment<T, A> newInstance(
      Observable<T> observable, ActivityNextMethod<T, A> resultCall, ActivityErrorMethod<A> errorMethod) {
    ObservableLoadFragment<T, A> fragmentProxy = new ObservableLoadFragment<>(observable, resultCall, errorMethod);
    return fragmentProxy;
  }

  class LoadingSubscriber extends Subscriber<TResult> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      onErrorInLoading(e);
    }

    @Override public void onNext(TResult result) {
      onResult(result);
    }
  }
}

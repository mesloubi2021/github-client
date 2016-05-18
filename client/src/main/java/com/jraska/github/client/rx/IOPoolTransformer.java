package com.jraska.github.client.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IOPoolTransformer<T> implements Observable.Transformer<T, T> {
  private static final IOPoolTransformer INSTANCE = new IOPoolTransformer();

  private IOPoolTransformer() {
  }

  @Override public Observable<T> call(Observable<T> observable) {
    return observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @SuppressWarnings("unchecked")
  public static <T> Observable.Transformer<T, T> get() {
    return INSTANCE;
  }
}

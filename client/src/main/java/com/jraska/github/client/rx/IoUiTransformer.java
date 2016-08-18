package com.jraska.github.client.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IoUiTransformer<T> implements Observable.Transformer<T, T> {
  private static final IoUiTransformer INSTANCE = new IoUiTransformer();

  private IoUiTransformer() {
  }

  @Override public Observable<T> call(Observable<T> observable) {
    return observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @SuppressWarnings("unchecked")
  @Deprecated
  public static <T> Observable.Transformer<T, T> get() {
    return INSTANCE;
  }

  public static final DataTransformerFactory FACTORY = new DataTransformerFactory() {
    @SuppressWarnings("unchecked")
    @Override public Observable.Transformer get() {
      return INSTANCE;
    }
  };
}

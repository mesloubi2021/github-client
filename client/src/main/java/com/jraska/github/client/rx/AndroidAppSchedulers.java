package com.jraska.github.client.rx;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class AndroidAppSchedulers {
  private AndroidAppSchedulers() {
  }

  public static AppSchedulers get() {
    return new AppSchedulers(AndroidSchedulers.mainThread(),
        Schedulers.io(), Schedulers.computation());
  }
}

package com.jraska.github.client.rx;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import rx.Observable;
import rx.functions.Action2;
import timber.log.Timber;

import javax.inject.Inject;

public class ObservableLoader {
  private final FragmentManager _fragmentManager;

  @Inject
  public ObservableLoader(FragmentManager fragmentManager) {
    _fragmentManager = fragmentManager;
  }

  public <R, A extends FragmentActivity> void load(Observable<R> observable,
                                                   Action2<A, R> resultCall,
                                                   Action2<A, Throwable> errorCall) {
    ObservableLoadFragment existingFragment = (ObservableLoadFragment) _fragmentManager.findFragmentByTag(ObservableLoadFragment.TAG);
    if (existingFragment != null && existingFragment.isValid()) {
      Timber.d("Activity %s is already loading its data", existingFragment.getActivity());
      return;
    }

    ObservableLoadFragment fragmentProxy = ObservableLoadFragment.newInstance(observable, resultCall, errorCall);
    _fragmentManager.beginTransaction()
        .add(fragmentProxy, ObservableLoadFragment.TAG)
        .commit();
  }
}

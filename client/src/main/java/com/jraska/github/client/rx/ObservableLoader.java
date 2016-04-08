package com.jraska.github.client.rx;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import rx.Observable;
import timber.log.Timber;

import javax.inject.Inject;

public class ObservableLoader {
  private final FragmentManager _fragmentManager;

  @Inject
  public ObservableLoader(FragmentManager fragmentManager) {
    _fragmentManager = fragmentManager;
  }

  public <TResult, TActivity extends Activity> void load(Observable<TResult> observable,
                                                         ActivityNextMethod<TResult, TActivity> activityNextMethod,
                                                         ActivityErrorMethod<TActivity> activityErrorMethod) {
    ObservableLoadFragment existingFragment = (ObservableLoadFragment) _fragmentManager.findFragmentByTag(ObservableLoadFragment.TAG);
    if (existingFragment != null && existingFragment.isValid()) {
      Timber.d("Activity %s is already loading its data", existingFragment.getActivity());
      return;
    }

    ObservableLoadFragment fragmentProxy = ObservableLoadFragment.newInstance(observable, activityNextMethod, activityErrorMethod);
    _fragmentManager.beginTransaction()
        .add(fragmentProxy, ObservableLoadFragment.TAG)
        .commit();
  }
}

package com.jraska.github.client.rx;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

public class ObservableLoader {
    private final FragmentManager _fragmentManager;

    @Inject
    public ObservableLoader(FragmentManager fragmentManager) {
        _fragmentManager = fragmentManager;
    }

    public <R, A extends FragmentActivity> void load(Observable<R> observable,
                                                     ResultDelegateProvider<A, R> resultDelegateProvider) {
        ObservableLoadFragment existingFragment = (ObservableLoadFragment) _fragmentManager.findFragmentByTag(ObservableLoadFragment.TAG);
        if (existingFragment != null && existingFragment.isValid()) {
            Timber.d("Activity %s is already loading its data", existingFragment.getActivity());
            existingFragment.requestDeliver();
            return;
        }

        ObservableLoadFragment fragmentProxy = ObservableLoadFragment.newInstance(observable, resultDelegateProvider);
        _fragmentManager.beginTransaction()
                .add(fragmentProxy, ObservableLoadFragment.TAG)
                .commit();
    }
}

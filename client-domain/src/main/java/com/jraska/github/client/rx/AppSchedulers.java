package com.jraska.github.client.rx;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public final class AppSchedulers {
  private final Scheduler mainThread;
  private final Scheduler io;
  private final Scheduler computation;

  private final SingleTransformer<?, ?> dataLoadTransformer;

  public AppSchedulers(Scheduler mainThread, Scheduler io, Scheduler computation) {
    this.mainThread = mainThread;
    this.io = io;
    this.computation = computation;

    dataLoadTransformer = new IoMainTransformer<>(this);
  }

  public Scheduler mainThread() {
    return mainThread;
  }

  public Scheduler io() {
    return io;
  }

  public Scheduler computation() {
    return computation;
  }

  @SuppressWarnings("unchecked")
  public <T> SingleTransformer<T, T> ioLoadTransformer() {
    return (SingleTransformer<T, T>) dataLoadTransformer;
  }

  static class IoMainTransformer<T> implements SingleTransformer<T, T> {
    private final AppSchedulers schedulers;

    IoMainTransformer(AppSchedulers schedulers) {
      this.schedulers = schedulers;
    }

    @Override public Single<T> apply(Single<T> observable) {
      return observable.subscribeOn(schedulers.io())
          .observeOn(schedulers.mainThread());
    }
  }
}

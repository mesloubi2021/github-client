package com.jraska.github.client.rx;

import rx.Scheduler;
import rx.Single;

public final class AppSchedulers {
  private final Scheduler mainThread;
  private final Scheduler io;
  private final Scheduler computation;

  private final Single.Transformer<?, ?> dataLoadTransformer;

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
  public <T> Single.Transformer<T, T> ioLoadTransformer() {
    return (Single.Transformer<T, T>) dataLoadTransformer;
  }

  static class IoMainTransformer<T> implements Single.Transformer<T, T> {
    private final AppSchedulers schedulers;

    IoMainTransformer(AppSchedulers schedulers) {
      this.schedulers = schedulers;
    }

    @Override public Single<T> call(Single<T> observable) {
      return observable.subscribeOn(schedulers.io())
          .observeOn(schedulers.mainThread());
    }
  }
}

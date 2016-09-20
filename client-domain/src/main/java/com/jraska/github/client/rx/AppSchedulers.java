package com.jraska.github.client.rx;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Scheduler;

public final class AppSchedulers {
  private final Scheduler mainThread;
  private final Scheduler io;
  private final Scheduler computation;

  private final Transformer<?, ?> dataLoadTransformer;

  AppSchedulers(Scheduler mainThread, Scheduler io, Scheduler computation) {
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
  public <T> Transformer<T, T> ioLoadTransformer() {
    return (Transformer<T, T>) dataLoadTransformer;
  }

  static class IoMainTransformer<T> implements Transformer<T, T> {
    private final AppSchedulers schedulers;

    IoMainTransformer(AppSchedulers schedulers) {
      this.schedulers = schedulers;
    }

    @Override public Observable<T> call(Observable<T> observable) {
      return observable.subscribeOn(schedulers.io())
          .observeOn(schedulers.mainThread());
    }
  }
}

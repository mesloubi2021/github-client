package com.jraska.github.client.rx;

import io.reactivex.Scheduler;

public final class AppSchedulers {
  private final Scheduler mainThread;
  private final Scheduler io;
  private final Scheduler computation;

  public AppSchedulers(Scheduler mainThread, Scheduler io, Scheduler computation) {
    this.mainThread = mainThread;
    this.io = io;
    this.computation = computation;
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
}

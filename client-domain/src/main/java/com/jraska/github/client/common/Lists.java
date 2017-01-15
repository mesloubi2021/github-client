package com.jraska.github.client.common;

import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

public final class Lists {
  private Lists() {
    Preconditions.throwNoInstances();
  }

  public static <R, T> List<R> transform(List<T> list, Func1<T, R> transform) {
    List<R> newList = new ArrayList<>();
    for (T item : list) {
      newList.add(transform.call(item));
    }

    return newList;
  }

  public static <T> List<T> filter(List<T> list, Func1<T, Boolean> predicate) {
    List<T> newList = new ArrayList<>();

    for (T item : list) {
      if (predicate.call(item)) {
        newList.add(item);
      }
    }

    return newList;
  }
}

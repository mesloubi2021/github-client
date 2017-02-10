package com.jraska.github.client.common;


import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class Lists {
  private Lists() {
    Preconditions.throwNoInstances();
  }

  public static <R, T> List<R> transform(List<T> list, Function<T, R> transform) {
    List<R> newList = new ArrayList<>();
    for (T item : list) {
      try {
        newList.add(transform.apply(item));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return newList;
  }

  public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    List<T> newList = new ArrayList<>();

    for (T item : list) {
      try {
        if (predicate.test(item)) {
          newList.add(item);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return newList;
  }
}

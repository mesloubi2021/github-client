package com.jraska.github.client.rx;

import rx.Observable;

public interface DataTransformerFactory {
  <T> Observable.Transformer<T, T> get();
}

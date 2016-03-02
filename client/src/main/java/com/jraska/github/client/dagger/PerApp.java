package com.jraska.github.client.dagger;

import javax.inject.Scope;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Scope @Retention(SOURCE) public @interface PerApp {
}

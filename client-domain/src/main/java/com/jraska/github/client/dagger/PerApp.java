package com.jraska.github.client.dagger;

import javax.inject.Scope;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// RUNTIME RetentionPolicy has to be set,
// otherwise Dagger cannot process properly graphs between modules
@Scope @Retention(RUNTIME) public @interface PerApp {
}

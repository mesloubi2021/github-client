package com.jraska.github.client.common;

import com.jraska.github.client.time.TestTimeProvider;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StopwatchTest {

  TestTimeProvider timeProvider;
  Stopwatch stopwatch;

  @Before
  public void setUp() {
    timeProvider = new TestTimeProvider();
    stopwatch = new Stopwatch(timeProvider);
  }

  @Test
  public void whenCreated_thenCorrectNormalFlow() {
    assertThat(stopwatch.running()).isFalse();
    assertThat(stopwatch.time()).isEqualTo(0);

    stopwatch.start();

    timeProvider.elapsed(5);
    assertThat(stopwatch.time()).isEqualTo(5);
    assertThat(stopwatch.running()).isTrue();
    timeProvider.elapsed(6);
    assertThat(stopwatch.time()).isEqualTo(6);

    stopwatch.stop();
    assertThat(stopwatch.running()).isFalse();

    timeProvider.elapsed(7);
    assertThat(stopwatch.time()).isEqualTo(6);
  }

  @Test
  public void whenReset_thenCorrectState() {
    stopwatch.start();

    timeProvider.advanceTime(4);

    stopwatch.reset();
    assertThat(stopwatch.running()).isFalse();
    assertThat(stopwatch.time()).isEqualTo(0);

    stopwatch.start();

    timeProvider.advanceTime(3);
    assertThat(stopwatch.time()).isEqualTo(3);
    assertThat(stopwatch.running()).isTrue();
  }

  @Test
  public void whenRestart_thenCOrrectState() {
    stopwatch.start();

    timeProvider.advanceTime(4);

    stopwatch.restart();
    assertThat(stopwatch.running()).isTrue();
    assertThat(stopwatch.time()).isEqualTo(0);

    timeProvider.advanceTime(3);
    assertThat(stopwatch.time()).isEqualTo(3);
    assertThat(stopwatch.running()).isTrue();
  }

  @Test
  public void whenStopCalledTwice_thenTimeDoesNotMove() {
    stopwatch.start();

    timeProvider.advanceTime(4);
    stopwatch.stop();

    timeProvider.advanceTime(2);
    assertThat(stopwatch.stop().time()).isEqualTo(4);
  }

  @Test
  public void whenStartOnStopped_thenCorrectTime() {
    stopwatch.start();

    timeProvider.advanceTime(4);
    stopwatch.stop();

    stopwatch.start();
    timeProvider.advanceTime(2);
    assertThat(stopwatch.stop().time()).isEqualTo(6);
  }
}
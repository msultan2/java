/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency.threads;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ThreadUtilsTest {

  private ThreadUtils instance;

  @Before
  public void setUp() {
    instance = new ThreadUtils();
  }

  @Test
  public void testDetermineNumberOfCores() {
    assertTrue(instance.determineNumberOfCores() > 2);
  }

  @Test
  public void testDetermineTotalNumberOfThreadsNeeded() {
    assertEquals(20, instance.determineTotalNumberOfThreadsNeeded(2, 0.9));
  }

}

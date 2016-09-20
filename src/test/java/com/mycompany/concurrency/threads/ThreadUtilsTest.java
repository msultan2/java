/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.concurrency.threads;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ThreadUtilsTest {

  private ThreadUtils instance;

  @Before
  public void setUp() {
    instance = new ThreadUtils();
  }

  @Test
  public void testDeterminingNumberOfThreads() {
    assertTrue(instance.determiningNumberOfThreads() > 2);
  }

}

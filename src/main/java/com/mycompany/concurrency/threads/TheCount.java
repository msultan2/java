/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency.threads;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Creating a Thread and Putting It to Sleep. From OCA/OCP Java SE7 Programmer 1 & 2 Study Guide,
 * Chapter 13: Threads.
 */
public class TheCount extends Thread {

  private static final int N = 100;
  private static final int ONE_SECOND = 1000;
  private static final int X = 10;

  public static void main(final String[] args) {
    Thread thread = new TheCount();
    thread.start();
  }

  @SuppressFBWarnings(value = "MDM_THREAD_YIELD", justification = "pause for 1 second")
  @Override
  public void run() {
    for (int i = 0; i <= N; i++) {
      try {
        Thread.sleep(ONE_SECOND);
      } catch (InterruptedException ex) {
        System.err.println(ex);
      }

      // output a string every ten numbers
      if (i % X == 0) {
        System.out.println("i = " + i);
      }
    }
  }

}

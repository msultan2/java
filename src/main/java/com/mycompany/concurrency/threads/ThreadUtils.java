/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency.threads;

public class ThreadUtils {

  public int determineNumberOfCores() {
    return Runtime.getRuntime().availableProcessors();
  }

  /**
   * Formula taken from Programming Concurrency on the JVM, p.17
   */
  public int determineTotalNumberOfThreadsNeeded(final int numberOfCores, final double blockingCoefficient) {
    return (int) (numberOfCores / (1 - blockingCoefficient));
  }

}

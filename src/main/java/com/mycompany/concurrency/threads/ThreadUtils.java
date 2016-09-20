/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency.threads;

public class ThreadUtils {

  public int determiningNumberOfThreads() {
    return Runtime.getRuntime().availableProcessors();
  }

}

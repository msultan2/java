/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.locks.Lock;

/**
 * Class to manage locks.
 */
public final class Locker {

  private Locker() {}

  /**
   * Wraps critical concurrent sections.
   */
  @SuppressFBWarnings(value = "MDM_WAIT_WITHOUT_TIMEOUT",
        justification = "Method is used only for demonstration purposes")
  public static void runLocked(final Lock lock, final Runnable block) {
    lock.lock();
    try {
      block.run();
    } finally {
      lock.unlock();
    }
  }

}

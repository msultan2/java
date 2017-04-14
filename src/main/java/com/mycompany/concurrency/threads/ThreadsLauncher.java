/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.concurrency.threads;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Helper class to execute other classes.
 */
public final class ThreadsLauncher {

  private ThreadsLauncher() {}

  public static void main(final String[] args) {
    executeInSync();
  }

  @SuppressFBWarnings(value = "LSYC_LOCAL_SYNCHRONIZED_COLLECTION", justification = "the reference is passed around")
  private static void executeInSync() {
    StringBuffer sb = new StringBuffer("A");

    new InSync(sb).start();
    new InSync(sb).start();
    new InSync(sb).start();
  }
}

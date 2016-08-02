/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class MyFileExecutor {

  private MyFileExecutor() {}

  public static void main(final String[] args) {
    Path file = Paths.get("test.txt");
    new FileGenerator().generateFile(file);
    MyFile.processStringsFromFile(file);

    Path file1 = Paths.get("eam.txt");
    Path file2 = Paths.get("eam2.txt");
    try {
      FileWriterEAM.use(file1, writerEAM -> writerEAM.writeStuff("sweet"));

      FileWriterEAM.use(file2, writerEAM -> {
        writerEAM.writeStuff("how");
        writerEAM.writeStuff("sweet");
      });
    } catch (IOException ex) {
      System.err.println("ERROR! Failed to write files.");
    }
  }
}

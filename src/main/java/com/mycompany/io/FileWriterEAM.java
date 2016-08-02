/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.io;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implements the execute around method (EAM) pattern.
 */
public final class FileWriterEAM implements AutoCloseable {

  private final Writer writer;

  private FileWriterEAM(final Path file) throws IOException {
    writer = Files.newBufferedWriter(file);
  }

  @Override
  public void close() throws IOException {
    System.out.println("close called automatically...");
    writer.close();
  }

  public void writeStuff(final String message) throws IOException {
    writer.write(message);
  }

  public static void use(final Path file, final UseInstance<FileWriterEAM, IOException> block)
          throws IOException {
    try (FileWriterEAM writerEAM = new FileWriterEAM(file)) {
      block.accept(writerEAM);
    }
  }

}

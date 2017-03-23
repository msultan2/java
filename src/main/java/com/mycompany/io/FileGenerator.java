/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class FileGenerator {

  private static final int NUMBER_OF_LINES = 700_000;
  private static final List<String> EXAMPLE_LINES = initializeExampleLines();

  private static List<String> initializeExampleLines() {
    List<String> exampleLines = new ArrayList<>();
    exampleLines.add("Blackened Recordings ");
    exampleLines.add("Divergent Series: Insurgent");
    exampleLines.add("Google Something");
    exampleLines.add("Pixels Movie Money");
    exampleLines.add("X Ambassadors");
    exampleLines.add("Power Path Pro Advanced");
    exampleLines.add("Something CYRFZQ");
    exampleLines.add("");
    return exampleLines;
  }

  public void generateFile(final Path file) {
    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
      for (int i = 0; i <= NUMBER_OF_LINES; i++) {
        String s = getRandomString();
        writer.write(s, 0, s.length());
        writer.newLine();
      }
    } catch (IOException ex) {
      System.err.format("IOException: %s%n", ex);
    }
  }

  private String getRandomString() {
    SecureRandom secRandom = new SecureRandom();
    int value = secRandom.nextInt(EXAMPLE_LINES.size());
    return EXAMPLE_LINES.get(value);
  }

}

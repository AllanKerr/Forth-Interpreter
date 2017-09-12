package com.kerr.interpreter.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Load a forth script from file and return the string representation of it. This is used
 * exclusively for loading the default script from file.
 * 
 * @author allankerr
 *
 */
class ScriptLoader {

  /**
   * The default extension for forth scripts.
   */
  private static final String SCRIPT_EXTENSION = ".fs";

  /**
   * Loads the script as a string from file using the default resources directory.
   * 
   * @param fileName The file name of the forth script excluding the ".fs" extension.
   * @return The string representation of the forth script with the specified file name
   * @throws URISyntaxException Thrown if the file name results in an invalid URI.
   * @throws IOException Thrown if the file cannot be found.
   */
  public static String load(String fileName) throws URISyntaxException, IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream input = classLoader.getResourceAsStream(fileName + SCRIPT_EXTENSION);
    if (input == null) {
      throw new IOException("Unable to find forth script " + fileName);
    }
    BufferedInputStream inputSteam = new BufferedInputStream(input);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    int result = inputSteam.read();
    while(result != -1) {
        output.write((byte) result);
        result = inputSteam.read();
    }
    return output.toString();
  }
}

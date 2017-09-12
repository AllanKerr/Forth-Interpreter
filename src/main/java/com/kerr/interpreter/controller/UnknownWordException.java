package com.kerr.interpreter.controller;

/**
 * An exception that is thrown when an unknown word is encountered during
 * script building. This means a typo occurred during the writing of the script.
 * @author allankerr
 *
 */
@SuppressWarnings("serial")
public class UnknownWordException extends Exception {

  public UnknownWordException(String message) {
    super(message);
  }
}

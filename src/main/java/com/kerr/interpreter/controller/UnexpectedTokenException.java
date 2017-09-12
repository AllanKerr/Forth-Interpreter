package com.kerr.interpreter.controller;

@SuppressWarnings("serial")
/**
 * An exception that is thrown when a script is being parsed and
 * a token is encountered in an unexpected location suggesting a
 * block is missing a termination statemnet.
 * @author allankerr
 *
 */
public class UnexpectedTokenException extends Exception {
  
  public UnexpectedTokenException(String message) {
    super(message);
  }
}

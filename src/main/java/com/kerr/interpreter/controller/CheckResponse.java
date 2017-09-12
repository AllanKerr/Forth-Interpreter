package com.kerr.interpreter.controller;

/**
 * Enum used as the return value for the interpreter's check word
 * to determine whether a nearby tile is occupied, empty, or out of bounds.
 * @author allankerr
 *
 */
public enum CheckResponse {
  EMPTY("EMPTY"),
  OCCUPIED("OCCUPIED"),
  OUT_OF_BOUNDS("OUT OF BOUNDS");
  
  /**
   * A string representation of the check response formatted
   * based on the interpreter's expected input.
   */
  private String name;       

  @Override
  public String toString() {
     return name;
  }
  
  private CheckResponse(String name) {
      this.name = name;
  }
}

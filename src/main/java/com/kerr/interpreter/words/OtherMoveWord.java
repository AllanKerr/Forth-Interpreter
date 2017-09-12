package com.kerr.interpreter.words;

/**
 * This class provides an alternate move word to recognize move! and move.
 * 
 * @author allankerr
 *
 */
public class OtherMoveWord extends MoveWord {
  @Override
  public String getName() {
    return "move";
  }
}

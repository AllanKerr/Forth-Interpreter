package com.kerr.interpreter.words;

/**
 * The base class that all predefined words subclass. All predefined words must have a name to allow
 * for lookup during script building.
 * 
 * @author allankerr
 *
 */
abstract class PredefinedWord extends Word {

  /**
   * The name of the word as defined in the RoboSport370 language specification.
   * 
   * @return The name of the word.
   */
  public abstract String getName();
}

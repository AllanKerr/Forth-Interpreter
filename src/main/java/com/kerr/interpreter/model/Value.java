package com.kerr.interpreter.model;

/**
 * A generic value class for representing values within the script
 * that are pushed to the stack during execution. Words perform
 * operations on values.
 * @author allankerr
 *
 * @param <V> The literal java type of the value.
 */
public abstract class Value <V> {
  
  /**
   * The value encountered during script execution that can be used
   * by words to perform operations.
   */
  protected V value;
  
  @Override
  public String toString() {
    return value.toString();
  }
  
  public V getValue() {
    return value;
  }
  
  /**
   * Constructs a new value that contains a specified value.
   * @param value The value to be stored.
   * @throws IllegalArgumentException Thrown if value is null.
   */
  public Value(V value) {
    if (value == null) {
      throw new IllegalArgumentException("Value must be constructued with a non-null value.");
    }
    this.value = value;
  }
}

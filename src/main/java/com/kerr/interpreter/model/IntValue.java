package com.kerr.interpreter.model;

/**
 * A value subclass for representing integer values. This
 * class is not strictly necessary but was created to
 * create more readable code and prevent errors by 
 * having IntValue appear rather than Value<Integer>.
 * @author allankerr
 *
 */
public class IntValue extends Value<Integer> {

  public IntValue(Integer value) {
    super(value);
  }
}

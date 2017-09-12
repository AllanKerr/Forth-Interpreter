package com.kerr.interpreter.model;

/**
 * A value subclass for representing boolean values. This
 * class is not strictly necessary but was created to
 * create more readable code and prevent errors by 
 * having BoolValue appear rather than Value<Boolean>.
 * @author allankerr
 *
 */
public class BoolValue extends Value<Boolean> {

  public BoolValue(Boolean value) {
    super(value);
  }
}

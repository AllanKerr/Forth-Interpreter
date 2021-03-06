package com.kerr.interpreter.model;

/**
 * A value subclass for representing variables values. This
 * class is not strictly necessary but was created to
 * create more readable code and prevent errors by 
 * having VariableValue appear rather than Value<String>.
 * This helps differentiate between variables and strings.
 * @author allankerr
 *
 */
public class VariableValue extends Value<String> {

  public VariableValue(String value) {
    super(value);
  }  
}

package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Pops the topmost boolean value from the stack, inverts its value, and pushes it to the stack.
 * 
 * @author allankerr
 *
 */
class InvertWord extends PredefinedWord {

  @Override
  public String getName() {
    return "invert";
  }

  /**
   * Pops the topmost boolean value from the stack, inverts its value, and pushes it to the stack.
   * 
   * @throws IllegalStateException Thrown if the topmost value on the stack is not a boolean.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    BoolValue val;
    try {
      val = (BoolValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("The condition for the if-word operation was not a boolean.");
    }
    BoolValue inverted = new BoolValue(!val.getValue());
    data.push(inverted);
  }

}

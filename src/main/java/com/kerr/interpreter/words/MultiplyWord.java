package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the two top-most values from the stack, multiplies them, and pushes the result to the stack.
 * 
 * @author allankerr
 *
 */
class MultiplyWord extends PredefinedWord {

  @Override
  public String getName() {
    return "*";
  }

  /**
   * Pops the two top-most values from the stack, multiplies them, and pushes the result to the
   * stack.
   * 
   * @throws IllegalStateException Thrown if either of the two top-most values aren't integers.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    IntValue val1;
    IntValue val2;
    try {
      val1 = (IntValue) data.pop();
      val2 = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "One of the arguments to '" + this.getName() + "' was not an integer.");
    }
    IntValue result = new IntValue(val1.getValue() * val2.getValue());
    data.push(result);
  }
}

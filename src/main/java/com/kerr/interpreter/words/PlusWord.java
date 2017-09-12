package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the two top-most values from the stack, adds them, and pushes the result.
 * 
 * @author allankerr
 *
 */
class PlusWord extends PredefinedWord {

  @Override
  public String getName() {
    return "+";
  }

  /**
   * Pops the two top-most values from the stack, adds them, and pushes the result.
   * 
   * @throws IllegalStateException Thrown if either of the top-most values are not integers.
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
    IntValue result = new IntValue(val1.getValue() + val2.getValue());
    data.push(result);
  }
}

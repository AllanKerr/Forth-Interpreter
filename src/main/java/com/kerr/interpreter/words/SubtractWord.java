package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.IntValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Pops the two top-most values from the stack and subtracts the subtracts the top-most value from
 * the one below it. The result is pushed back to the stack.
 * 
 * @author allankerr
 *
 */
class SubtractWord extends PredefinedWord {

  @Override
  public String getName() {
    return "-";
  }

  /**
   * Pops the two top-most values from the stack and subtracts the subtracts the top-most value from
   * the one below it. The result is pushed back to the stack.
   * 
   * @throws IllegalStateException Thrown if one of the two top-most values are not integers.
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
    IntValue result = new IntValue(val2.getValue() - val1.getValue());
    data.push(result);
  }
}

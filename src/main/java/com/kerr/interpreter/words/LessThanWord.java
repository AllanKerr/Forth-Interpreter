package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the two top-most integers from the stack and pushes true if the second from the top integer
 * is less than the top integer or false if it isn't.
 * 
 * @author allankerr
 *
 */
class LessThanWord extends PredefinedWord {

  @Override
  public String getName() {
    return "<";
  }

  /**
   * Pops the two top-most integers from the stack and pushes true if the second from the top
   * integer is less than the top integer or false if it isn't.
   * 
   * @throws IllegalStateException Thrown if either of the top-most values are not integers.
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
    BoolValue result = new BoolValue(val2.getValue() < val1.getValue());
    data.push(result);
  }
}

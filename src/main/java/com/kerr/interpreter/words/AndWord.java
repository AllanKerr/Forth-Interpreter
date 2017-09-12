package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Performs the and operation for evaluating if the two top-most values in the stack are both true.
 * 
 * @author allankerr
 *
 */
class AndWord extends PredefinedWord {

  @Override
  public String getName() {
    return "and";
  }

  /**
   * Pops two boolean values from the stack and pushes to the stack true if they are both equal and
   * false if they aren't.
   * 
   * @throws IllegalStateException Thrown if one of the top two values is not a boolean.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    BoolValue val1;
    BoolValue val2;
    try {
      val1 = (BoolValue) data.pop();
      val2 = (BoolValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "One of the parameters to '" + getName() + "' operation was not a boolean");
    }
    BoolValue result = new BoolValue(val1.getValue() && val2.getValue());
    data.push(result);
  }
}

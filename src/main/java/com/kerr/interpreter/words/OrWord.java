package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Pops the two top-most values from the stack and pushes true to the stack if either one of them
 * are true.
 * 
 * @author allankerr
 *
 */
class OrWord extends PredefinedWord {

  @Override
  public String getName() {
    return "or";
  }

  /**
   * Pops the two top-most values from the stack and pushes true to the stack if either one of them
   * are true. If they are both false then false is pushed to the stack.
   * 
   * @throws IllegalStateException Thrown if either of the two top-most values from the stack are
   *         not boolean values.
   *
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
    BoolValue result = new BoolValue(val1.getValue() || val2.getValue());
    data.push(result);
  }
}

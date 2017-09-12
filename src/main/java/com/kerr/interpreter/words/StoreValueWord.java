package com.kerr.interpreter.words;

import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.Value;
import com.kerr.interpreter.model.VariableValue;

/**
 * Pops the two top-most values from the stack. The top-most is used as the variable name and the
 * value below it is used as the new value to assign to the variable name.
 * 
 * @author allankerr
 *
 */
class StoreValueWord extends PredefinedWord {

  @Override
  public String getName() {
    return "!";
  }

  /**
   * Pops the two top-most values from the stack. The top-most is used as the variable name and the
   * value below it is used as the new value to assign to the variable name.
   * 
   * @throws IllegalStateException Thrown if the top-most value is not a variable name.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    VariableValue variable;
    try {
      variable = (VariableValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Attempted to set the value of an invalid variable name.");
    }
    Value<?> val = data.pop();
    data.setVariable(variable, val);
  }
}

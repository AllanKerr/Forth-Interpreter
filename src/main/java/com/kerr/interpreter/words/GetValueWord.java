package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.VariableValue;

/**
 * Pops a variable's name from the top of the stack and pushes the value currently stored in that
 * variable to the stack.
 * 
 * @author allankerr
 *
 */
class GetValueWord extends PredefinedWord {

  @Override
  public String getName() {
    return "?";
  }

  /**
   * Pops a variable's name from the top of the stack and pushes to the top of the stack the value
   * currently stored in that variable.
   * 
   * @throws IllegalStateException Thrown if the topmost value on the stack is not a variable name.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    VariableValue variable;
    try {
      variable = (VariableValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Attempted to get the value of an invalid variable name.");
    }
    data.pushVariable(variable);
  }
}

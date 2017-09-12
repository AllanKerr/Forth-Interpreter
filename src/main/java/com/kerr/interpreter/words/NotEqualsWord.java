package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.Value;

/**
 * Pops the two top-most values from the stack and pushes true to the top of the stack if they
 * aren't equal to each other.
 * 
 * @author allankerr
 *
 */
class NotEqualsWord extends PredefinedWord {

  @Override
  public String getName() {
    return "<>";
  }

  /**
   * Pops the two top-most values from the stack and pushes true to the top of the stack if they
   * aren't equal to each other. If they are equal, false is pushed.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val1 = data.pop();
    Value<?> val2 = data.pop();
    BoolValue result = new BoolValue(!val1.getValue().equals(val2.getValue()));
    data.push(result);
  }
}

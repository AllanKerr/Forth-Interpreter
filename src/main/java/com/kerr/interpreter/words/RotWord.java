package com.kerr.interpreter.words;

import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.Value;

/**
 * Rotates the three top-most values on the stack. This takes the value third from the top and moves
 * it to the top of the stack by shifting the other two values down one.
 * 
 * @author allankerr
 *
 */
class RotWord extends PredefinedWord {

  @Override
  public String getName() {
    return "rot";
  }

  /**
   * Pops the three top-most values off the stack. The value third from the top is pushed back at
   * the top with the other two being shifted down one.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val1 = data.pop();
    Value<?> val2 = data.pop();
    Value<?> val3 = data.pop();
    data.push(val2);
    data.push(val1);
    data.push(val3);
  }
}

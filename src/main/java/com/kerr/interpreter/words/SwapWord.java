package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.Value;

/**
 * Swaps the two top-most values on the stack.
 * 
 * @author allankerr
 *
 */
class SwapWord extends PredefinedWord {

  @Override
  public String getName() {
    return "swap";
  }

  /**
   * Pops the two top-most values from the stack and pushes them back in the opposite order.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val1 = data.pop();
    Value<?> val2 = data.pop();
    data.push(val1);
    data.push(val2);
  }
}

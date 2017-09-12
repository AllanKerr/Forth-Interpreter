package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.Value;

/**
 * Duplicates the top-most item in the stack.
 * 
 * @author allankerr
 *
 */
class DupWord extends PredefinedWord {

  @Override
  public String getName() {
    return "dup";
  }

  /**
   * Pops the top-most item from the stack and pushes it to the top of the stack twice to duplicate
   * it.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val = data.pop();
    data.push(val);
    data.push(val);
  }
}

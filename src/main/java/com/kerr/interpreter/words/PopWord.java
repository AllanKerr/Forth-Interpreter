package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;

/**
 * Removes the top-most value from the stack.
 * 
 * @author allankerr
 *
 */
class PopWord extends PredefinedWord {

  @Override
  public String getName() {
    return "pop";
  }

  /**
   * Removes the top-most value from the stack.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.pop();
  }
}

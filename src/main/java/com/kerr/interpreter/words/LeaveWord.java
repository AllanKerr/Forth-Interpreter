package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;

/**
 * Leaves the body of the currently executing loop.
 * 
 * @author allankerr
 *
 */
class LeaveWord extends PredefinedWord {

  @Override
  public String getName() {
    return "leave";
  }

  /**
   * Throws a LeaveLoopException to exit the body of the loop which is caught by the loop-word
   * executing the body.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    throw new LeaveLoopException();
  }
}

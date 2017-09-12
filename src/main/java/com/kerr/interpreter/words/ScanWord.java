package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Asks the script listener for the number of robots within visible range of the robot the script is
 * being executed for and pushes it to the stack.
 * 
 * @author allankerr
 *
 */
class ScanWord extends PredefinedWord {

  @Override
  public String getName() {
    return "scan!";
  }

  /**
   * Asks the script listener for the number of robots within visible range of the robot the script
   * is being executed for and pushes it to the stack.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    IntValue result = new IntValue(listener.scan());
    data.push(result);
  }
}

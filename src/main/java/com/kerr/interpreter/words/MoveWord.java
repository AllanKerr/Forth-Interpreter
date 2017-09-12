package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;

/**
 * Notifies the script listener that the robot has moved.
 *
 */
class MoveWord extends PredefinedWord {

  @Override
  public String getName() {
    return "move!";
  }

  /**
   * Notifies the script listener that the robot has moved.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    listener.move();
  }
}

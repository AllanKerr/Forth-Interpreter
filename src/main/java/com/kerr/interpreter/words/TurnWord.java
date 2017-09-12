package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.IntValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Notifies the script listener that the robot has turned and by how much.
 * 
 * @author allankerr
 *
 */
class TurnWord extends PredefinedWord {

  @Override
  public String getName() {
    return "turn!";
  }

  /**
   * Pops the top value from the stack which should be the direction of how much the robot turned by
   * and sends to to the script listener.
   * 
   * @throws IllegalStateException Thrown if the top value was not an integer.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    IntValue val;
    try {
      val = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "The arguments to '" + this.getName() + "' was not an integer.");
    }
    listener.turn(val.getValue());
  }
}

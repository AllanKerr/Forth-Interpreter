package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;
import com.kerr.interpreter.controller.IdentifyResponse;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the topmost value from the stack and uses that value to identify the robot in that
 * direction. The robot's team color, range, direction, and remaining health are pushed to the
 * stack.
 * 
 * @author allankerr
 *
 */
class IdentifyWord extends PredefinedWord {

  @Override
  public String getName() {
    return "identify!";
  }

  /**
   * Pops the topmost value from the stack and uses that value to identify the robot in that
   * direction. The robot's team color, range, direction, and remaining health are pushed to the
   * stack.
   * 
   * @throws IllegalStateException Thrown if the stack's topmost value is not an integer.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {

    IntValue val;
    try {
      val = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "The parameters for the " + getName() + " operation was not an integer");
    }
    IdentifyResponse response = listener.identify(val.getValue());
    StringValue team = new StringValue(response.getTeam().toString());
    IntValue range = new IntValue(response.getRange());
    IntValue direction = new IntValue(response.getDirection());
    IntValue remainingHealth = new IntValue(response.getRemainingHealth());
    data.push(remainingHealth);
    data.push(direction);
    data.push(range);
    data.push(team);
  }
}

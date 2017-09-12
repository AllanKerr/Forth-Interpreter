package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;

/**
 * Indicates whether the robot has a waiting message from the team member obtained by poping the
 * top-most value from the stack.
 * 
 * @author allankerr
 *
 */
class MessageWord extends PredefinedWord {

  @Override
  public String getName() {
    return "mesg?";
  }

  /**
   * Pops the top-most value from the stack and uses it as the team member to check if their is from
   * it. True is pushed to the stack if there is mail; otherwise, false is pushed.
   * 
   * @throws IllegalStateException Thrown if the top-most value was not a valid robot type. Robot
   *         Types may be SCOUT, SNIPER, or TANK.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    StringValue senderVal;
    try {
      senderVal = (StringValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "The arguments to '" + this.getName() + "' was not a string.");
    }
    RobotType sender = RobotType.valueOf(senderVal.getValue());
    data.push(new BoolValue(data.hasMessage(sender)));
  }
}

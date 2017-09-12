package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;
import com.kerr.interpreter.model.Value;

/**
 * Predefined word to send a string, integer, or boolean value to the mailbox of a team member by
 * notifying the script listener.
 * 
 * @author allankerr
 *
 */
class SendWord extends PredefinedWord {

  @Override
  public String getName() {
    return "send!";
  }

  /**
   * Pops the two top-most values from the stack. The top-most value is used as the value to be sent
   * and the value below it is used to determine the robot type of the team member to which the
   * message should be send to. This may be SCOUT, SNIPER, or TANK.
   * 
   * @throws IllegalStateException Thrown if the robot type was not a string.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val = data.pop();
    StringValue senderVal;
    try {
      senderVal = (StringValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "One of the arguments to '" + this.getName() + "' was not of the expected type.");
    }
    RobotType sender = RobotType.valueOf(senderVal.getValue());
    boolean result = listener.sendMessage(sender, val);
    data.push(new BoolValue(result));
  }
}

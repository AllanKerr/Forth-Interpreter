package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;
import com.kerr.interpreter.model.RobotType;

/**
 * Pushes the value in the mailbox corresponding to the specified team member to the top of the
 * stack.
 * 
 * @author allankerr
 *
 */
class ReceiveWord extends PredefinedWord {

  @Override
  public String getName() {
    return "recv!";
  }

  /**
   * Pops the top-most value from the stack to be used as the team member robot type. This type is
   * used to push the first value in its mailbox to the stack.
   * @throws IllegalStateException Thrown if the top-most value is not a valid robot type.
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
    data.pushMessage(sender);
  }
}

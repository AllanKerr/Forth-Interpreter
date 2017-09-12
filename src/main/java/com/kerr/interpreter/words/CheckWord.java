package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.CheckResponse;
import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;
import com.kerr.interpreter.model.IntValue;

/**
 * Scans for nearby robots and pushes the number of robots to the stack.
 * 
 * @author allankerr
 *
 */
class CheckWord extends PredefinedWord {

  @Override
  public String getName() {
    return "check!";
  }

  /**
   * Asks the listener to scan for robots in visible range and pushes the number of nearby robots to
   * the stack.
   * 
   * @throws IllegalArgumentException Thrown if an invalid check response is received.
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
    CheckResponse response = listener.check(val.getValue());
    if (response == null) {
      throw new IllegalArgumentException("Script received a null check response.");
    }
    StringValue result = new StringValue(response.toString());
    data.push(result);
  }
}

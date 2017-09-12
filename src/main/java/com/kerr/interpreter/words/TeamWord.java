package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;

/**
 * The predefined word for getting the team of the robot the script is being run for. This may be
 * RED, BLUE, or ORANGE, PURPLE, GREEN, or BLUE.
 * 
 * @author allankerr
 *
 */
public class TeamWord extends PredefinedWord {

  @Override
  public String getName() {
    return "team";
  }

  /**
   * Requests the robot team from the data source and pushes it to the stack.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new StringValue(dataSource.team().toString()));
  }
}

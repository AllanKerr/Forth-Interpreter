package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;

/**
 * The predefined word for getting the type of the robot the script is being run for. This may be
 * SCOUT, SNIPER, or TANK.
 * 
 * @author allankerr
 *
 */
public class TypeWord extends PredefinedWord {

  @Override
  public String getName() {
    return "type";
  }

  /**
   * Requests the robot type from the data source and pushes it to the stack.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new StringValue(dataSource.type().toString()));
  }
}

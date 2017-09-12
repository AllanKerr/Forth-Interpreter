package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * The predefined word for getting the visible range of the robot the script is being executed for.
 * 
 * @author allankerr
 *
 */
public class RangeWord extends PredefinedWord {

  @Override
  public String getName() {
    return "range";
  }

  /**
   * Requests the visible range of the robot from the data source and pushes it to the stack.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new IntValue(dataSource.range()));
  }
}

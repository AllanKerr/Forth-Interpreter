package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * The predefined word for getting the number of moves the robot the script is being executed for
 * started with.
 * 
 * @author allankerr
 *
 */
public class MovesWord extends PredefinedWord {

  @Override
  public String getName() {
    return "moves";
  }

  /**
   * Asks the data source for the number of moves the robot the script is being executed for started
   * with and pushes this value to the stack.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new IntValue(dataSource.moves()));
  }
}

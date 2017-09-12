package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pushes the data source's health value to the stack.
 * 
 * @author allankerr
 *
 */
public class HealthWord extends PredefinedWord {

  @Override
  public String getName() {
    return "health";
  }

  /**
   * Pushes the data source's health value to the stack.
   * 
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new IntValue(dataSource.health()));
  }
}

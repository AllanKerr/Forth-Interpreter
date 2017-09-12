package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pushes the data source's remaining health value to the stack.
 * 
 * @author allankerr
 *
 */
public class HealthLeftWord extends PredefinedWord {

  @Override
  public String getName() {
    return "healthLeft";
  }

  /**
   * Pushes the data source's remaining health value to the stack.
   * 
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    data.push(new IntValue(dataSource.healthLeft()));
  }
}

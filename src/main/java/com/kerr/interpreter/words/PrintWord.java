package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.Value;

/**
 * Pops the top-most value from the stack and prints it to standard output.
 * @author allankerr
 *
 */
class PrintWord extends PredefinedWord {

  @Override
  public String getName() {
    return ".";
  }

  /**
   * Pops the top-most value from the stack and prints it to standard output.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    Value<?> val = data.pop();
    System.out.println(val);
  }
}

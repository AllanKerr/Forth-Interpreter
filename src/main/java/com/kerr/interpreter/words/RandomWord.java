package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import java.util.Random;

import com.kerr.interpreter.model.IntValue;

/**
 * Pushes a random value to the stack generated inclusively within a specified range.
 * 
 * @author allankerr
 *
 */
class RandomWord extends PredefinedWord {

  @Override
  public String getName() {
    return "random";
  }

  /**
   * Pops the top-most value from the stack to be used as the inclusive upper bound. Pushes a random
   * value between 0 and the specified value to the stack.
   * 
   * @throws IllegalStateException Thrown if the top-most value is not an integer.
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
    IntValue result = new IntValue(new Random().nextInt(val.getValue() + 1));
    data.push(result);
  }
}

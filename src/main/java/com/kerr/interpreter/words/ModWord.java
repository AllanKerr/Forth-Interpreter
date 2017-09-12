package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the two top-most values from the stack, divides them and pushes the remainder and quotient
 * to the stack.
 * 
 * @author allankerr
 *
 */
class ModWord extends PredefinedWord {

  @Override
  public String getName() {
    return "/mod";
  }

  /**
   * Pops the two top-most values from the stack. Integer division is performed on these values. The
   * quotient is pushed to the top of the stack with the remainder beneath it.
   * 
   * @throws IllegalStateException Thrown if either of the two top-most values are not integers.
   *
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    IntValue val1;
    IntValue val2;
    try {
      val1 = (IntValue) data.pop();
      val2 = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "One of the arguments to '" + this.getName() + "' was not an integer.");
    }
    IntValue remainder = new IntValue(val2.getValue() % val1.getValue());
    IntValue quotient = new IntValue(val2.getValue() / val1.getValue());
    data.push(remainder);
    data.push(quotient);
  }
}

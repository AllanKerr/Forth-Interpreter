package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;
import com.kerr.interpreter.model.Value;
import com.kerr.interpreter.model.VariableValue;

/**
 * Executes the loop's body until the loop's start value is equal to the loop's end value.
 * 
 * @author allankerr
 *
 */
public class CountedLoopWord extends LoopWord {

  @Override
  public String toString() {
    return indentedToString(0);
  }

  @Override
  protected String indentedToString(int indents) {
    return indentedToString(indents, "for");
  }

  public CountedLoopWord(MultiWord body) {
    super(body);
  }

  /**
   * Pops two values from the stack for the start value and upper bound of the for-loop. Executes
   * the body until the start value is equal to the upper bound.
   * 
   * @throws IllegalStateException Thrown if either of the two topmost values on the stack are not
   *         integers.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {

    // Save the counter variable in case of being in a nested loop.
    VariableValue counter = new VariableValue("I");
    Value<?> oldVal = data.getVariable(counter);

    IntValue startVal;
    IntValue endVal;
    try {
      startVal = (IntValue) data.pop();
      endVal = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "The start or end value of a counted loop was not of the expected type");
    }
    try {
      for (int i = startVal.getValue(); i <= endVal.getValue(); i++) {
        data.setVariable(counter, new IntValue(i));
        body.execute(data, listener, dataSource);
      }
    } catch (LeaveLoopException ex) {
      // Leave loop exception is used as a pseudo goto statement to jump out
      // of as many levels of nesting from if statements and multi-words
      // when a leave statement is encountered inside a loop
    }
    // Restore the old counter value
    data.setVariable(counter, oldVal);
  }
}

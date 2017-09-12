package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;

/**
 * Executes the loop's body until the loop's guard statement becomes true.
 * 
 * @author allankerr
 *
 */
public class GuardedLoopWord extends LoopWord {

  @Override
  public String toString() {
    return indentedToString(0);
  }

  @Override
  protected String indentedToString(int indents) {
    return indentedToString(indents, "do-while");
  }

  public GuardedLoopWord(MultiWord body) {
    super(body);
  }

  /**
   * Executes the body until the guard statement becomes true. The guard statement is checked last
   * as in a do-while loop.
   * @throws IllegalStateException Thrown if the loop's guard statement is not a boolean.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {

    try {
      BoolValue finishCondition;
      do {
        body.execute(data, listener, dataSource);
        try {
          finishCondition = (BoolValue) data.pop();
        } catch (ClassCastException ex) {
          throw new IllegalStateException("The guard value for the guarded loop was not a boolean value as expected.");
        }
      } while (finishCondition.getValue());
    } catch (LeaveLoopException ex) {
      // Leave loop exception is used as a pseudo goto statement to jump out
      // of as many levels of nesting from if statements and multi-words
      // when a leave statement is encountered inside a loop
    }
  }
}

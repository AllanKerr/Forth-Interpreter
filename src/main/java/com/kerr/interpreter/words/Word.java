package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;

/**
 * Base abstract word class that all words subclass. It provides the base for all executable words.
 * 
 * @author allankerr
 *
 */
public abstract class Word {

  /**
   * A helper method for printing compiled scripts the the correct level of indenting.
   * 
   * @param indents The number of indents to add.
   * @return The formatted string representation of the word.
   */
  protected String indentedToString(int indents) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append(this.toString());
    return builder.toString();
  }

  /**
   * Execute must be implemented by all subclasses. It is used to specify what the word does when it
   * executes by manipulating the stack, accessing variables, or communicating with the
   * ScripListener or ScriptDataSource.
   * 
   * @param data The script that is being executed used to access the stack and variables.
   * @param listener The listener that output is sent to. A few examples include when a robot moves,
   *        turns, shoots, or scans.
   * @param dataSource The data source that provides information on the robot the script is being
   *        executed for such as health remaining, moves remaing, and shots remaining.
   */
  public abstract void execute(ScriptData data, ScriptListener listener,
      ScriptDataSource dataSource);
}

package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.Value;
import com.kerr.interpreter.model.VariableValue;

/**
 * This word represents a literal value within the script being executed. This may be a string,
 * integer, boolean, or variable name. When executed, its value is pushed to the stack.
 * 
 * @author allankerr
 *
 */
public class LiteralWord extends Word {

  /**
   * The literal value that the word represents.
   */
  private Value<?> value;

  /**
   * Constructs a new literal word with specified value.
   * 
   * @param value
   */
  public LiteralWord(Value<?> value) {
    super();
    this.value = value;
  }

  /**
   * Pushes the literal word's value to the stack. If the value is a variable named I then the
   * script must be within a counted loop. In this case, the value of the counter should be pushed.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    if (value.getValue().equals("I")) {
      VariableValue variable = new VariableValue("I");
      data.pushVariable(variable);
    } else {
      data.push(value);
    }
  }

}

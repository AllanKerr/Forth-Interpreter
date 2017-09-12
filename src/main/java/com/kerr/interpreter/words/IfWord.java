package com.kerr.interpreter.words;

import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.BoolValue;

/**
 * Pops the topmost value from the stack and executes the true case if it is true or the false case
 * if it is false.
 * 
 * @author allankerr
 *
 */
public class IfWord extends Word {

  protected MultiWord trueCase;

  protected MultiWord falseCase;

  @Override
  public String toString() {
    return indentedToString(0);
  }

  /**
   * Formatted print statement for keeping block indenting consistent during printing of nesting
   * blocks.
   */
  @Override
  protected String indentedToString(int indents) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append("if {\n");
    builder.append(trueCase.indentedToString(indents + 1));
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append("} else {\n");
    builder.append(falseCase.indentedToString(indents + 1));
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append("}");
    if (indents <= 0) {
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Constructs a new if-word with the specified true and false cases.
   * 
   * @param trueCase The body of the true case to be executed.
   * @param falseCase The body of the false case to be executed.
   */
  public IfWord(MultiWord trueCase, MultiWord falseCase) {
    super();
    if (trueCase == null) {
      throw new IllegalArgumentException("Attempted to construct an if-word with a null true case");
    }
    if (falseCase == null) {
      throw new IllegalArgumentException(
          "Attempted to construct an if-word with a null false case");
    }
    this.trueCase = trueCase;
    this.falseCase = falseCase;
  }

  /**
   * Pops the topmost value from the stack and executes the true case if it is true or the false
   * case if it is false.
   * 
   * @throws IllegalStateException Thrown if the topmost value on the stack is not a boolean.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    BoolValue val;
    try {
      val = (BoolValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("The condition for the if-word operation was not a boolean.");
    }
    if (val.getValue()) {
      trueCase.execute(data, listener, dataSource);
    } else {
      falseCase.execute(data, listener, dataSource);
    }
  }

}

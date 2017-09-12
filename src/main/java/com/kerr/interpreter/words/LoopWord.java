package com.kerr.interpreter.words;

/**
 * The base abstract class that all control loops subclass. It contains a body that is executed
 * sequentially a variable number of times.
 * 
 * @author allankerr
 *
 */
public abstract class LoopWord extends Word {

  /**
   * The body to execute 0 or more times.
   */
  protected MultiWord body;

  protected String indentedToString(int indents, String name) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append(name + "{\n");
    builder.append(body.indentedToString(indents + 1));
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
   * Constructs a new loop word with the specified body.
   * 
   * @param body The body to be executed each time the loop runs.
   */
  public LoopWord(MultiWord body) {
    this.body = body;
  }
}

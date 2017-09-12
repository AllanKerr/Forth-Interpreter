package com.kerr.interpreter.controller;

/**
 * An enum representing tokens with special meaning 
 * within the script. These represent control tokens,
 * literals, and stand-alone executable words.
 * @author allankerr
 *
 */
enum ScriptToken {
  BEGIN_STATEMENT("begin"),
  UNTIL_STATEMENT("until"),
  DO_STATEMENT("do"),
  LOOP_STATEMENT("loop"),
  IF_STATEMENT("if"),
  ELSE_STATEMENT("else"),
  THEN_STATEMENT("then"),
  BLOCK_START(":"),
  BLOCK_END(";"),
  VARIABLE("variable"),
  INTEGER,
  STRING,
  BOOLEAN,
  WORD,
  COMMENT;
  
  /**
   * The actual string the token will be represented with
   * inside scripts that are being parsed.
   */
  private String name;       

  @Override
  public String toString() {
     return name;
  }
  
  private ScriptToken(String name) {
      this.name = name;
  }
  
  private ScriptToken() {}
}

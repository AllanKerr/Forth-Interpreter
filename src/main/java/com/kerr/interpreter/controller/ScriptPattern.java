package com.kerr.interpreter.controller;

import java.util.regex.Pattern;

/**
 * Represents a complex pattern within the script that are used during
 * script tokenization by the script builder. A complex pattern must have
 * a start pattern, end pattern and content between. Comments ( -- ) and
 * strings ."content" are examples of these.
 * @author allankerr
 *
 */
class ScriptPattern {

  /**
   * The token type the pattern as a whole represents.
   * ex: The whole pattern may be that of a comment or string.
   */
  private ScriptToken token;
  
  /**
   * The regular expression that matches the start of the pattern.
   */
  private Pattern startPattern;
  
  /**
   * The regular expression that matches the end of the pattern.
   */
  private Pattern endPattern;
  
  /**
   * The regular expression that matches the whole pattern
   * including the start, content, and end. This is used for cases
   * where the pattern content doesn't include whitespace.
   */
  private Pattern wholePattern;
  
  /**
   * The number of characters in the start of the pattern.
   */
  private int startLength;

  /**
   * The number of characters in the end of the pattern.
   */
  private int endLength;

  public ScriptToken getScriptToken() {
    return token;
  }
  
  public Pattern getStartPattern() {
    return startPattern;
  }

  public Pattern getEndPattern() {
    return endPattern;
  }
  
  public Pattern getWholePattern() {
    return wholePattern;
  }

  public int getStartLength() {
    return startLength;
  }

  public int getEndLength() {
    return endLength;
  }

  public ScriptPattern(ScriptToken token, String startPattern, String endPattern, String wholePattern, int startLength, int endLength) {
    super();
    this.token = token;
    this.startPattern = Pattern.compile(startPattern);
    this.endPattern = Pattern.compile(endPattern);
    this.wholePattern = Pattern.compile(wholePattern);
    this.startLength = startLength;
    this.endLength = endLength;
  }
}

package com.kerr.interpreter.controller;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is responsible for breaking scripts up into the raw tokens that they consist of. The
 * list of tokens that result can be found in the ScriptToken enum.
 * 
 * @author allankerr
 *
 */
class ScriptTokenizer {

  /**
   * A pattern to change the scanner to during parsing script patterns based on the ScriptPattern
   * class. This pattern includes whitespace so as to not lose it in the body of complex patterns
   * that represent singular tokens.
   */
  private static final Pattern LOOKAROUND_DELIMITER = Pattern.compile("(?=(\\s+))");

  /**
   * The complex pattern used to match strings within the script being parsed.
   */
  private static final ScriptPattern STRING_PATTERN =
      new ScriptPattern(ScriptToken.STRING, "\\.\".*", ".*\"", "\\.\".*\"", 2, 1);

  /**
   * The complex pattern used to match comments within the script being parsed.
   */
  private static final ScriptPattern COMMENT_PATTERN =
      new ScriptPattern(ScriptToken.COMMENT, "\\(.*", ".*\\)", "\\(.*\\)", 1, 1);

  /**
   * The value of the token that was most recently parsed using next. It may be an integer, boolean,
   * string, or not have a value.
   */
  private Object value;

  /**
   * The token type of the token that was most recently parsed using next. Used to throw an
   * exception to ensure the user isn't accessing the wrong type.
   */
  private ScriptToken token;

  private Scanner scanner;

  private List<ScriptPattern> patterns;

  /**
   * The integer value of the last scanned token.
   * 
   * @return The integer value.
   * @throws InputMismatchException Thrown if the last parsed token was not an integer.
   */
  public int getIntValue() {
    if (token != ScriptToken.INTEGER) {
      throw new InputMismatchException();
    }
    return (Integer)value;
  }

  /**
   * The boolean value of the last scanned token.
   * 
   * @return The boolean value.
   * @throws InputMismatchException Thrown if the last parsed token was not a boolean.
   */
  public boolean getBoolValue() {
    if (token != ScriptToken.BOOLEAN) {
      throw new InputMismatchException();
    }
    return (Boolean)value;
  }

  /**
   * The comment string of the last scanned token.
   * 
   * @return The comment string.
   * @throws InputMismatchException Thrown if the last parsed token was not a comment.
   */
  public String getCommentValue() {
    if (token != ScriptToken.COMMENT) {
      throw new InputMismatchException();
    }
    return (String)value;
  }

  /**
   * The string value of the last scanned token.
   * 
   * @return The string value.
   * @throws InputMismatchException Thrown if the last parsed token was not a string.
   */
  public String getStringValue() {
    if (token != ScriptToken.STRING) {
      throw new InputMismatchException();
    }
    return (String)value;
  }

  /**
   * The word value of the last scanned token.
   * 
   * @return The word value.
   * @throws InputMismatchException Thrown if the last parsed token was not word.
   */
  public String getWordValue() {
    if (token != ScriptToken.WORD) {
      throw new InputMismatchException();
    }
    return (String)value;
  }

  /**
   * The variable value of the last scanned token.
   * 
   * @return The variable value.
   * @throws InputMismatchException Thrown if the last parsed token was not variable.
   */
  public String getVariable() {
    if (token != ScriptToken.VARIABLE) {
      throw new InputMismatchException();
    }
    return (String)value;
  }

  /**
   * Construct a tokenizer for parsing the specified script.
   * @param script The string representation of the script to be parsed.
   * @throws IllegalArgumentException Thrown if the script is null.
   */
  public ScriptTokenizer(String script) {
    if (script == null) {
      throw new IllegalArgumentException("Script tokenizer must be initialized with a non-null script.");
    }
    scanner = new Scanner(script);
    patterns = new LinkedList<ScriptPattern>();
    patterns.add(STRING_PATTERN);
    patterns.add(COMMENT_PATTERN);
  }

  /**
   * Determines whether or not there is another token left to parse.
   * @return True if there is a next token or false if all the tokens have been parsed.
   */
  public boolean hasNext() {
    return scanner.hasNext();
  }

  /**
   * Parses the next token in the script.
   * @return The type of the next token that was parsed.
   * @throws NoSuchElementException Thrown if the script does not have a next element.
   */
  public ScriptToken nextToken() {
    // Keep track of the current token type for input checking
    token = getNextToken();
    return token;
  }

  /**
   * Parses the next token in the script.
   * @return The type of the next token that was parsed.
   * @throws NoSuchElementException Thrown if the script does not have a next element.
   */
  private ScriptToken getNextToken() {
    if (!scanner.hasNext()) {
      throw new NoSuchElementException();
    }
    if (scanner.hasNextInt()) {
      value = scanner.nextInt();
      return ScriptToken.INTEGER;
    } else if (scanner.hasNextBoolean()) {
      value = scanner.nextBoolean();
      return ScriptToken.BOOLEAN;
    } else if (scanner.hasNext(ScriptToken.BLOCK_START.toString())) {
      scanner.next();
      return ScriptToken.BLOCK_START;
    } else if (scanner.hasNext(ScriptToken.BLOCK_END.toString())) {
      scanner.next();
      return ScriptToken.BLOCK_END;
    } else if (scanner.hasNext(ScriptToken.VARIABLE.toString())) {
      // Skip the variable token to get its name
      scanner.next();
      value = scanner.next();
      return ScriptToken.VARIABLE;
    } else if (scanner.hasNext(ScriptToken.IF_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.IF_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.ELSE_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.ELSE_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.THEN_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.THEN_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.DO_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.DO_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.LOOP_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.LOOP_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.BEGIN_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.BEGIN_STATEMENT;
    } else if (scanner.hasNext(ScriptToken.UNTIL_STATEMENT.toString())) {
      scanner.next();
      return ScriptToken.UNTIL_STATEMENT;
    } else {
      boolean foundMatch = false;
      ScriptPattern pattern = null;
      for (int i = 0; (i < patterns.size()) && !foundMatch; i++) {
        pattern = patterns.get(i);
        foundMatch = scanner.hasNext(pattern.getStartPattern());
      }
      if (foundMatch) {
        value = parsePattern(pattern);
        return pattern.getScriptToken();
      } else {
        value = scanner.next();
        return ScriptToken.WORD;
      }
    }
  }

  /**
   * Parses a complex token encountered within the script.
   * @param pattern The pattern of complex token found within the script
   *    based on matching its start pattern.
   * @return The string representation of the parsed pattern.
   */
  private String parsePattern(ScriptPattern pattern) {
    if (scanner.hasNext(pattern.getWholePattern())) {
      // The pattern didn't include any white spaces
      String match = scanner.next(pattern.getWholePattern());
      return match.substring(pattern.getStartLength(), match.length() - pattern.getEndLength());
    } else {

      // Parse while including white spaces
      String part = scanner.next(pattern.getStartPattern());
      String component = part.substring(pattern.getStartLength());
      StringBuilder builder = new StringBuilder();

      // Save the standard delimiter for once parsing is finished
      Pattern delimiter = scanner.delimiter();

      // Switch to a look around delimiter to prevent loss of spacing in strings
      scanner = scanner.useDelimiter(LOOKAROUND_DELIMITER);

      boolean foundEnd = false;
      while (!foundEnd) {
        builder.append(component);
        foundEnd = scanner.hasNext(pattern.getEndPattern());
        component = scanner.next();
      }
      // Switch back to standard delimiter
      scanner.useDelimiter(delimiter);

      // Substring to remove ending quotation
      builder.append(component.subSequence(0, component.length() - pattern.getEndLength()));
      return builder.toString();
    }
  }
}

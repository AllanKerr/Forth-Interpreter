package com.kerr.interpreter.controller;

import com.kerr.interpreter.model.BoolValue;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.StringValue;
import com.kerr.interpreter.words.IfWord;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kerr.interpreter.model.IntValue;
import com.kerr.interpreter.model.Value;
import com.kerr.interpreter.model.VariableValue;
import com.kerr.interpreter.words.CountedLoopWord;
import com.kerr.interpreter.words.GuardedLoopWord;
import com.kerr.interpreter.words.LiteralWord;
import com.kerr.interpreter.words.MultiWord;
import com.kerr.interpreter.words.PredefinedWords;
import com.kerr.interpreter.words.Word;

/**
 * A class for parsing scripts into tokens that are either variables, the start and end of blocks,
 * the start and end of loops, the start and end of if-statements, words, and literals such as
 * string, boolean, or int.
 * 
 * @author allankerr
 *
 */
class ScriptBuilder {

  /**
   * Parses the script into tokens and builds the word tree structure based on the resulting tokens.
   * 
   * @param teamName The name of the team that wrote the script.
   * @param scriptName The name of the script being built.
   * @param script The string represented of the script to be parsed.
   * @return
   * @throws UnexpectedTokenException Thrown when the script is missing a block end token
   * @throws UnknownWordException Thrown when an invalid word is found in the script.
   */
  public ScriptData build(String teamName, String scriptName, String script)
      throws UnexpectedTokenException, UnknownWordException {

    Map<String, Value<?>> variables = new HashMap<String, Value<?>>();
    Map<String, Word> userWords = new HashMap<String, Word>();
    variables.put("I", new IntValue(0));

    List<Word> buildWords = new LinkedList<Word>();

    Word start;
    MultiWord buildWord;

    ScriptTokenizer tokenizer = new ScriptTokenizer(script);
    while (tokenizer.hasNext()) {
      ScriptToken token = tokenizer.nextToken();
      switch (token) {

        case VARIABLE:
          // Add variable to the map for script data
          String variableName = tokenizer.getVariable();
          variables.put(variableName, new IntValue(0));
          tokenizer.nextToken();
          break;


        case COMMENT:
          break;


        case WORD:
          // When a word is found outside of a start an end block, this means it belongs to a block
          // of code that is executed when the script is built usually to initialize variables.
          start = getWord(tokenizer.getWordValue(), variables, userWords);
          buildWord = new MultiWord(start, build(tokenizer, variables, userWords));
          buildWords.add(buildWord);

          // The start block may be followed through from a build word which is not part of a block.
          // If the build word is the last word in the script being built then there will be no next
          // block.
          startBlock(tokenizer, variables, userWords);
          break;

        case BOOLEAN:
          // Handles a build word that starts with a boolean value
          start = new LiteralWord(new BoolValue(tokenizer.getBoolValue()));
          buildWord = new MultiWord(start, build(tokenizer, variables, userWords));
          buildWords.add(buildWord);

          // Parse any user defined word that comes after the set of build words.
          startBlock(tokenizer, variables, userWords);
          break;

        case STRING:
          // Handles a build word that starts with a string value
          start = new LiteralWord(new StringValue(tokenizer.getStringValue()));
          buildWord = new MultiWord(start, build(tokenizer, variables, userWords));
          buildWords.add(buildWord);

          // Parse any user defined word that comes after the set of build words.
          startBlock(tokenizer, variables, userWords);
          break;

        case INTEGER:
          // Handles a build word that starts with a integer value
          start = new LiteralWord(new IntValue(tokenizer.getIntValue()));
          buildWord = new MultiWord(start, build(tokenizer, variables, userWords));
          buildWords.add(buildWord);

          // Parse any user defined word that comes after the set of build words.
          startBlock(tokenizer, variables, userWords);
          break;

        case BLOCK_START:
          startBlock(tokenizer, variables, userWords);
          break;

        default:
          // All other tokens should be handled in the build private method.
          // Encountering a token suggests an improperly formatted script.
          throw new UnexpectedTokenException(
              "Encountered unexpected token '" + token.toString() + "' while building the script");
      }
    }
    ScriptData data = new ScriptData(teamName, scriptName, userWords, variables);
    for (Word word : buildWords) {
      word.execute(data, null, null);
    }
    return data;
  }

  /**
   * Starts the parsing of a block inside a forth script denoted by the block start ":" and the
   * block end ";". The resulting word is added to the list of user defined words.
   * 
   * @param tokenizer The tokenizer being used to build the script
   * @param variables The variables that have been encountered so far during script execution
   * @param userWords The user-defined words that have been encountered so far used for inline
   *        compilation.
   * @throws UnexpectedTokenException Thrown when the script is missing a block end token
   * @throws UnknownWordException Thrown when an invalid word is found in the script.
   */
  private void startBlock(ScriptTokenizer tokenizer, Map<String, Value<?>> variables,
      Map<String, Word> userWords) throws UnexpectedTokenException, UnknownWordException {
    if (tokenizer.hasNext()) {
      // Add word to scriptData
      tokenizer.nextToken();
      String wordName = tokenizer.getWordValue();
      MultiWord word = build(tokenizer, variables, userWords);
      userWords.put(wordName, word);
    }
  }

  /**
   * Builds the multi-word based on the words between the start of the body and the end of the body.
   * The body starts when ':', 'begin', 'do', 'if', or else tokens are encountered. The body ends
   * when ';', 'until', 'loop', 'else' , or 'then' tokens are encountered. This method may be called
   * recursively if a body start is found while building the current body.
   * 
   * @param tokenizer The tokenizer being used to build the script
   * @param variables The variables that have been encountered so far during script execution
   * @param userWords The user-defined words that have been encountered so far used for inline
   *        compilation.
   * @return The multi-word resulted from parsing the script between the start statement end
   *         statement.
   * @throws UnexpectedTokenException Thrown when the script is missing a block end token
   * @throws UnknownWordException Thrown when an invalid word is found in the script.
   */
  private MultiWord build(ScriptTokenizer tokenizer, Map<String, Value<?>> variables,
      Map<String, Word> userWords) throws UnexpectedTokenException, UnknownWordException {
    PredefinedWords predefinedWords = PredefinedWords.getInstance();
    LinkedList<Word> words = new LinkedList<Word>();
    while (tokenizer.hasNext()) {
      ScriptToken token = tokenizer.nextToken();
      switch (token) {
        // Handles the start of a for loop
        case BEGIN_STATEMENT:
          MultiWord guardedBody = build(tokenizer, variables, userWords);
          GuardedLoopWord gaurdedLoop = new GuardedLoopWord(guardedBody);
          words.add(gaurdedLoop);
          break;

        // Handles the start of a do-while loop
        case DO_STATEMENT:
          MultiWord countedBody = build(tokenizer, variables, userWords);
          CountedLoopWord countedLoop = new CountedLoopWord(countedBody);
          words.add(countedLoop);
          break;

        // Handles the start of an if-statement
        case IF_STATEMENT:
          MultiWord trueCase = build(tokenizer, variables, userWords);
          MultiWord falseCase = build(tokenizer, variables, userWords);
          IfWord ifWord = new IfWord(trueCase, falseCase);
          words.add(ifWord);
          break;

        // Handles all other words
        case WORD:
          String name = tokenizer.getWordValue();
          if (variables.containsKey(name)) {
            VariableValue variableValue = new VariableValue(name);
            Word stringWord = new LiteralWord(variableValue);
            words.add(stringWord);
          } else if (predefinedWords.hasWord(name)) {
            words.add(predefinedWords.getWord(name));
          } else if (userWords.containsKey(name)) {
            words.add(userWords.get(name));
          } else {
            throw new UnknownWordException(
                "Unknown word '" + name + "' encountered during parsing.");
          }
          break;

        // Handles string literal tokens
        case STRING:
          StringValue stringValue = new StringValue(tokenizer.getStringValue());
          Word stringWord = new LiteralWord(stringValue);
          words.add(stringWord);
          break;

        // Handles integer literal tokens
        case INTEGER:
          IntValue intValue = new IntValue(tokenizer.getIntValue());
          Word intWord = new LiteralWord(intValue);
          words.add(intWord);
          break;

        // Handles boolean literal tokens
        case BOOLEAN:
          BoolValue boolValue = new BoolValue(tokenizer.getBoolValue());
          Word boolWord = new LiteralWord(boolValue);
          words.add(boolWord);
          break;

        // All of these tokens cause the block of words to be returned.
        // Each one is the end block of a body statement in scripts.
        case UNTIL_STATEMENT:
        case LOOP_STATEMENT:
        case ELSE_STATEMENT:
        case THEN_STATEMENT:
        case BLOCK_END:
        case BLOCK_START:
          return new MultiWord(words);
        default:
          break;
      }
    }
    // One of the end of body statement tokens must be found or the script was wrongly formatted.
    throw new UnexpectedTokenException(
        "Failed to find terminating character while building the script.");
  }

  private Word getWord(String name, Map<String, Value<?>> variables, Map<String, Word> userWords)
      throws UnknownWordException {
    PredefinedWords predefinedWords = PredefinedWords.getInstance();
    if (variables.containsKey(name)) {
      VariableValue variableValue = new VariableValue(name);
      return new LiteralWord(variableValue);
    } else if (predefinedWords.hasWord(name)) {
      return predefinedWords.getWord(name);
    } else if (userWords.containsKey(name)) {
      return userWords.get(name);
    } else {
      throw new UnknownWordException("Unknown word '" + name + "' encountered during parsing.");
    }
  }
}

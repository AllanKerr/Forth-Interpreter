package com.kerr.interpreter.controller;


import com.kerr.interpreter.model.ScriptData;
import java.io.IOException;
import java.net.URISyntaxException;

import com.kerr.interpreter.words.Word;

/**
 * Primary controller class for interfacing with the interpreter component found in the design.The
 * building and executing of scripts are delegated to other classes within the interpreter from this
 * class.
 * 
 * @author allankerr
 *
 */
public class ScriptController {

  /**
   * Responsible for parsing and building word-tree representation of scripts.
   */
  private ScriptBuilder builder = new ScriptBuilder();

  /**
   * All script output such as move, shoot, check, and mailbox messages are passed to be handled by
   * the listener.
   */
  private ScriptListener listener;

  /**
   * Construct a new script controller.
   * 
   * @param listener The listener for which script execution output should be sent to.
   * @throw IllegalArgumentException Thrown if listener is null.
   */
  public ScriptController(ScriptListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Script controller must have a non-null listener.");
    }
    this.listener = listener;
  }

  /**
   * Construct a new script controller exclusively for building scripts.
   * 
   */
  public ScriptController() {

  }

  /**
   * Parses the script into tokens and builds the word-tree structure based on the resulting tokens.
   * 
   * @param script The string represented of the script to be parsed.
   * @return
   * @throws UnexpectedTokenException Thrown when the script is missing a block end token
   * @throws UnknownWordException Thrown when an invalid word is found in the script.
   * @throws IllegalArgumentException Thrown if script is null.
   */
  public ScriptData build(String teamName, String scriptName, String script)
      throws UnexpectedTokenException, UnknownWordException {
    if (script == null) {
      throw new IllegalArgumentException("Attempted to build a null script.");
    }
    return builder.build(teamName, scriptName, script);
  }

  /**
   * Parses the script that is loaded from the resources directory and builds the word-tree
   * structure based on the resulting tokens.
   * 
   * @param fileName The name of the file in the resources directory to load the script from. This
   *        file name should not include the extension.
   * @return
   * @throws UnexpectedTokenException Thrown when the script is missing a block end token
   * @throws UnknownWordException Thrown when an invalid word is found in the script.
   * @throws IllegalArgumentException Thrown if script is null.
   * @throws URISyntaxException Thrown if the file name results in an invalid URI.
   * @throws IOException Thrown if the file cannot be found.
   */
  public ScriptData build(String fileName)
      throws UnexpectedTokenException, UnknownWordException, URISyntaxException, IOException {
    if (fileName == null) {
      throw new IllegalArgumentException("Attempted to load script with null file name.");
    }
    String script = ScriptLoader.load(fileName);
    return build(null, null, script);
  }

  /**
   * Executes the pre-built script with all messages that interact with the board being sent to the
   * script listener.
   * 
   * @param data The pre-built script to be executed.
   * @param dataSource The data provider for script providing information on the robot the script is
   *        controlling such as remaining health and moves.
   * @throws IllegalArgumentException Thrown if dataSource is null.
   */
  public void execute(final ScriptData data, final ScriptDataSource dataSource) {
    if (listener == null) {
      throw new IllegalStateException(
          "A script controller must have a non-null script listener to execute scripts.");
    }
    if (dataSource == null) {
      throw new IllegalArgumentException("A script must be executed with a non-null data source.");
    }
    new Thread(new Runnable() {
      public void run() {
        Word playWord = data.getPlayWord();
        try {
          playWord.execute(data, listener, dataSource);
        } catch (Exception ex) {
          listener.scriptInterrupted(ex);
        }
        listener.scriptFinished();
      }
    }).start();
  }
}

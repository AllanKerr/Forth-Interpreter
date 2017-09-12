package com.kerr.interpreter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.kerr.interpreter.words.Word;

/**
 * The ScriptData class is used to store scripts once they have been built. This is used by the
 * system's model component for storing scripts that have been built and executing them at later
 * times.
 * 
 * @author allankerr
 *
 */
public class ScriptData implements Cloneable {

  /**
   * The number of items that can fit in the mailbox for each robot.
   */
  private static final int MAILBOX_CAPACITY = 6;

  /**
   * The user-defined word all scripts must have to start script execution.
   */
  private static final String PLAY_WORD = "play";


  /**
   * The name of the team who built the script.
   */
  private String teamName;

  /**
   * The name of the script that was built.
   */
  private String scriptName;

  /**
   * The stack that values are pushed to and popped from by words during script execution.
   */
  private Stack<Value<?>> stack;

  /**
   * The user defined words that were found during script building. Every script must have a play
   * word.
   */
  private Map<String, Word> words;

  /**
   * The variables that were doing during script building used to store persistent state between
   * multiple executions of the script.
   */
  private Map<String, Value<?>> variables;

  /**
   * The mailboxes that store the values sent to the robot that the script is used to control with a
   * mailbox used for each team member.
   */
  private ArrayList<ArrayList<Value<?>>> mailboxes;

  /**
   * Determines whether the script was one of the default ones loaded directly from file or if it
   * came from the robot librarian.
   * 
   * @return True if the script was a default script or false if it came from the robot librarian.
   */
  public boolean isDefault() {
    return teamName == null && scriptName == null;
  }

  public String getTeamName() {
    return teamName;
  }

  public String getScriptName() {
    return scriptName;
  }

  /**
   * Saves a message that was sent from another script that was currently being executed by storing
   * it in the mailbox reserved for messages from the specified sender.
   * 
   * @param sender The type of the robot who sent the message.
   * @param value The value the robot sent as the message.
   * @return True if the mailbox had room for the message to be added or false if it was full and
   *         couldn't be saved.
   */
  public boolean saveMessage(RobotType sender, Value<?> value) {
    ArrayList<Value<?>> mailbox = mailboxes.get(sender.ordinal());
    if (mailbox.size() < MAILBOX_CAPACITY) {
      mailbox.add(value);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Construct a new script consisting of the user defined words and variables found during script
   * building.
   * 
   * @param words The user-defined words found during script building.
   * @param variables The variables found during script building.
   * @throws IllegalArgumentException Thrown if words is null.
   * @throws IllegalArgumentException Thrown if variables is null.
   * @throws IllegalArgumentException Thrown if words does not contain the play word.
   */
  public ScriptData(String teamName, String scriptName, Map<String, Word> words,
      Map<String, Value<?>> variables) {
    super();

    if (words == null) {
      throw new IllegalArgumentException(
          "The user-defined words for a built script cannot be null");
    }
    if (variables == null) {
      throw new IllegalArgumentException("The variables for a built script cannot be null");
    }
    if (!words.containsKey(PLAY_WORD)) {
      throw new IllegalArgumentException(
          "A user-defined play word was not found during script building.");
    }
    mailboxes = new ArrayList<ArrayList<Value<?>>>(RobotType.values().length);
    for (int i = 0; i < RobotType.values().length; i++) {
      mailboxes.add(new ArrayList<Value<?>>(MAILBOX_CAPACITY));
    }
    this.teamName = teamName;
    this.scriptName = scriptName;
    this.stack = new Stack<Value<?>>();
    this.words = words;
    this.variables = variables;
  }

  /**
   * Gets the play word used to start script execution.
   * 
   * @return The play word.
   */
  public Word getPlayWord() {
    return this.words.get(PLAY_WORD);
  }

  /**
   * Determines if the there is a message in the mailbox corresponding to the specified robot type.
   * 
   * @param sender The robot type of the mailbox to be checked.
   * @return True if the mailbox contains a message or false if it doesn't.
   */
  public boolean hasMessage(RobotType sender) {
    return mailboxes.get(sender.ordinal()).size() > 0;
  }

  /**
   * Pushes the next value sent by the team member with with the matching robot type to the stack.
   * 
   * @param sender The type of the robot who sent the message.
   */
  public void pushMessage(RobotType sender) {
    ArrayList<Value<?>> mailbox = mailboxes.get(sender.ordinal());
    this.push(mailbox.remove(0));
  }

  /**
   * Pushes the value of the specified variable to the stack.
   * 
   * @param variable The value that stores the name of the variable.
   */
  public void pushVariable(VariableValue variable) {
    Value<?> value = variables.get(variable.getValue());
    this.push(value);
  }

  /**
   * Updates a value of
   * 
   * @param variable
   * @param value
   */
  public void setVariable(VariableValue variable, Value<?> value) {
    variables.put(variable.getValue(), value);
  }

  public Value<?> getVariable(VariableValue variable) {
    return variables.get(variable.getValue());
  }

  /**
   * Pops the top most value from the stack for a word to perform an operation on.
   * 
   * @return The value that was at the top of the stack.
   */
  public Value<?> pop() {
    if (stack.isEmpty()) {
      throw new IllegalArgumentException("Cannot pop value from an empty stack.");
    }
    return stack.pop();
  }

  /**
   * Push a value to the top of the stack during script execution.
   * 
   * @param value The value to be pushed to the top of the stack.
   */
  public void push(Value<?> value) {
    if (value == null) {
      throw new IllegalArgumentException("A null value cannot be pushed to the stack.");
    }
    stack.push(value);
  }
  
  @Override
  public ScriptData clone() {
    try {
      ScriptData clone = (ScriptData) super.clone();
      clone.variables = new HashMap<String,Value<?>>(variables);
      clone.mailboxes = new ArrayList<ArrayList<Value<?>>>(mailboxes);
      return clone;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

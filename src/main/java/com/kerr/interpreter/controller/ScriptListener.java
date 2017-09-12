package com.kerr.interpreter.controller;

import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.Value;

/**
 * Interface for handling output from script execution such as checking
 * for nearby robots, moving, and shooting.
 * @author allankerr
 *
 */
public interface ScriptListener {
    
  /**
   * Informs the listener that the script executed a turn instruction.
   * @param direction The direction to turn relative to the current direction.
   */
  public void turn(int direction);

  /**
   * Informs the listener that the script executed a move instruction.
   */
  public void move();

  /**
   * Informs the listener that the script executed a shoot instruction.
   * @param direction The direction the tile is at relative to the current direction.
   * @param distance The distance away for the point to shoot at relative to the current point.
   */
  public void shoot(int direction, int distance);

  /**
   * Gets the number of robots that are within visible range of the robot the script
   * is being run for.
   * @return The number of robots in visible range.
   */
  public int scan();

  /**
   * Identifies a robot that is within range based ranging from 0 to scan() - 1.
   * @param index The index of the robot based on the number of robots found in the scan.
   * @return The team color, range, direction, and remaining health of the robot
   *    at the specified index.
   */
  public IdentifyResponse identify(int index);
  
  /**
   * Checks that a tile in a specified direction is empty, occupied, or out of bounds.
   * @param direction The direction of the tile to check in the range of 0 to 5.
   * @return
   */
  public CheckResponse check(int direction);

  /**
   * Sends a message to a robot with the specified type on the same team
   * as the robot the script is being run for.
   * @param type The type of the robot to send the message to.
   * @param value The value to send to the robot.
   * @return true or false depending on if the message was successfully  send.
   */
  public boolean sendMessage(RobotType type, Value<?> value);
  
  /**
   * Informs the listener that an error was encountered while executing the script
   * causing execution to be interrupted.
   * @param exception The exception causing the script to be interrupted.
   */
  public void scriptInterrupted(Exception exception);
  
  /**
   * Informs the listener that the script finished executing successfully.
   */
  public void scriptFinished();
}

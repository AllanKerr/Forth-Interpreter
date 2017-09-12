package com.kerr.interpreter.controller;

import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.TeamColor;

/**
 * Interface for providing data to an executing script on the robot
 * being controlled such as moves and health remaining. Based on the
 * design the data source is a robot with the system's model.
 * @author allankerr
 *
 */
public interface ScriptDataSource {

  /**
   * Get the starting health of the robot the script is being run for.
   * @return The robot's starting health.
   */
  public int health();
  
  /**
   * Get the remaining health of the robot the script is being run for.
   * @return The robot's remaining health.
   */
  public int healthLeft();
  
  /**
   * Get the starting movings of the robot the script is being run for.
   * @return The robot's starting moves.
   */
  public int moves();
  
  /**
   * Get the remaining moves of the robot the script is being run for.
   * @return The robot's remaining moves.
   */
  public int movesLeft();
  
  /**
   * Get the damage done by the robot the robot the script is being run for
   * when it shoots.
   * @return The robot's attack damage.
   */
  public int attack();
  
  /**
   * Get the visibility and shoot range for the robot the script being run for.
   * @return The robot's visible range.
   */
  public int range();
  
  /**
   * Get the team color for the robot the script is being run for belongs to.
   * @return The robot's team color.
   */
  public TeamColor team();
  
  /**
   * Get the robot type for the robot the script is being run for belongs to.
   * @return The robot's team color.
   */
  public RobotType type();
}

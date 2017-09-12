package com.kerr.interpreter.controller;

import com.kerr.interpreter.model.TeamColor;

/**
 * Identify response is used as the return value for the interpreter's identify word
 * for checking getting team, range, direction, and health of a robot in a nearby tile.
 * @author allankerr
 *
 */
public class IdentifyResponse {

  /**
   * The team of the robot that is being checked.
   */
  private TeamColor team;
  
  /**
   * The range of the robot that is being checked.
   */
  private int range;
  
  /**
   * The direction of the robot that is being checked.
   */
  private int direction;
  
  /**
   * The remaining health of the robot that is being checked.
   */
  private int remainingHealth;
  
  public TeamColor getTeam() {
    return team;
  }

  public int getRange() {
    return range;
  }

  public int getDirection() {
    return direction;
  }

  public int getRemainingHealth() {
    return remainingHealth;
  }

  public IdentifyResponse(TeamColor team, int range, int direction, int remainingHealth) {
    super();
    this.team = team;
    this.range = range;
    this.direction = direction;
    this.remainingHealth = remainingHealth;
  }
}

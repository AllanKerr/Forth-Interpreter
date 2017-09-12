package com.kerr.interpreter.model;

/**
 * There are 6 possible colors for a team to be, and there can only be one team
 * associated with any particular color.
 * @author C2
 */
public enum TeamColor {
  RED("RED"),
  GREEN("GREEN"),
  YELLOW("YELLOW"),
  BLUE("BLUE"),
  ORANGE("ORANGE"),
  PURPLE("PURPLE");
  
  private String name;     

  @Override
  public String toString() {
     return name;
  }
  
  private TeamColor(String name) {
      this.name = name;
  }
}

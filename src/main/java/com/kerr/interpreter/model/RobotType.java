package com.kerr.interpreter.model;

/**
 * Different types of available robots to be played. Each has individual characteristics that
 * make it unique from the others. Each team will consist of one robot of each different types.
 * @author C2
 *
 */
public enum RobotType {
  SCOUT("SCOUT", 1, 1, 3, 2),
  SNIPER("SNIPER", 2, 2, 2, 3),
  TANK("TANK", 3, 3, 1, 1);
  
  private String name;
  private int health;
  private int moves;
  private int attack;
  private int range;
  
  @Override
  public String toString() {
     return name;
  }
  
  private RobotType(String name, int attack, int health, int moves, int range) {      
      this.attack = attack;
      this.health = health;
      this.moves = moves;
      this.range = range;
      this.name = name;
  }
  
  public int getHealth(){
    return health;
  }
  
  public int getMoves(){
    return moves;
  }
  
  public int getAttack(){
    return attack;
  }
  
  public int getRange(){
    return range;
  }
}

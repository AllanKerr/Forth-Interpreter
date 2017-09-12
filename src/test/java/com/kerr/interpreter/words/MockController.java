package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.CheckResponse;
import com.kerr.interpreter.controller.IdentifyResponse;
import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.TeamColor;
import com.kerr.interpreter.model.Value;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * This class acts as a mock controller for testing the script controller. It implements the bare
 * minimum functionality to act as a ScriptListener and ScriptDataSource. The output from the script
 * is tracked to allow for comparing to expected values.
 * 
 * @author allankerr
 *
 */
public class MockController implements ScriptListener, ScriptDataSource {

  private int direction = 0;
  
  private int moveCount = 0;
  
  private int shotCount = 0;
  
  private List<Integer> checks = new LinkedList<Integer>();

  private int scanCount = 0;
  
  private int identifyCount = 0;
  
  private List<Value<?>> messages = new LinkedList<Value<?>>();
  
  private boolean interrupted = false;
  
  private boolean finished = false;
  
  private RobotType type;
  
  private CountDownLatch latch;
  
  public int getDirection() {
    return direction;
  }

  public int getMoveCount() {
    return moveCount;
  }

  public int getShotCount() {
    return shotCount;
  }

  public List<Integer> getChecks() {
    return checks;
  }

  public int getScanCount() {
    return scanCount;
  }

  public int getIdentifyCount() {
    return identifyCount;
  }

  public List<Value<?>> getMessages() {
    return messages;
  }

  public boolean isInterrupted() {
    return interrupted;
  }

  public boolean isFinished() {
    return finished;
  }

  public MockController(RobotType mockType, CountDownLatch latch) {
    this.type = mockType;
    this.latch = latch;
  }
  
  public void turn(int direction) {
    this.direction = ((this.direction + direction) + 6) % 6;
  }

  public void move() {
    moveCount++;    
  }

  public void shoot(int direction, int distance) {
    shotCount++;    
  }

  public int scan() {
    scanCount++;
    return 0;
  }

  public IdentifyResponse identify(int index) {
    identifyCount++;
    return null;
  }

  public CheckResponse check(int direction) {
    checks.add(direction);
    return CheckResponse.EMPTY;
  }

  public boolean sendMessage(RobotType type, Value<?> value) {
    messages.add(value);
    return true;
  }

  public void scriptInterrupted(Exception exception) {
    interrupted = true;    
    latch.countDown();
  }

  public void scriptFinished() {
    finished = true;
    latch.countDown();
  }

  public int health() {
    return type.getHealth();
  }

  public int healthLeft() {
    return type.getHealth();
  }

  public int moves() {
    return type.getMoves();
  }

  public int movesLeft() {
    return Math.max(type.getMoves() - this.moveCount, 0);
  }

  public int attack() {
    return type.getAttack();
  }

  public int range() {
    return type.getRange();
  }

  public TeamColor team() {
    return TeamColor.RED;
  }

  public RobotType type() {
    return type;
  }
}

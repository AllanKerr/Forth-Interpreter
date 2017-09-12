package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.model.IntValue;

/**
 * Pops the two top-most values from the stack to be used as the distance and direction that the
 * robot should shoot at. These values are then sent to the script listener to notify it that the
 * robot shot.
 */
class ShootWord extends PredefinedWord {

  @Override
  public String getName() {
    return "shoot!";
  }

  /**
   * Pops the two top-most values from the stack to be used as the distance and direction that the
   * robot should shoot at. These values are then sent to the script listener to notify it that the
   * robot shot.
   * 
   * @throws IllegalStateException Thrown if either of the two values at the top of the stack aren't
   *         integers.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    IntValue distance;
    IntValue direction;
    try {
      distance = (IntValue) data.pop();
      direction = (IntValue) data.pop();
    } catch (ClassCastException ex) {
      throw new IllegalStateException(
          "One of the arguments to '" + this.getName() + "' was not an integer.");
    }
    listener.shoot(direction.getValue(), distance.getValue());
  }
}

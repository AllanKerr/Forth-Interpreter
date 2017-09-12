package com.kerr.interpreter.words;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.kerr.interpreter.controller.ScriptController;
import com.kerr.interpreter.controller.UnexpectedTokenException;
import com.kerr.interpreter.controller.UnknownWordException;
import com.kerr.interpreter.model.RobotType;
import com.kerr.interpreter.model.ScriptData;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

/**
 * This class is used to test the ScriptController class and its ability to produce the expected
 * output when executing scripts. Three scripts are executed during the tests. These are simple,
 * moderate, and complex complexity scripts.
 * 
 * @author allankerr
 *
 */
public class ScriptControllerTest {
  
  /**
   * Test that the ScriptController produces the expected output when executing a compiled script.
   * It is done using a simple script with no looping or conditional statements.
   */
  @Test
  public void runSimpleScript()
      throws UnexpectedTokenException, UnknownWordException, InterruptedException {
    String script =  
        ": maxRange 3 ;                             \n" +               
        ": hexesPerRange 6 ;                        \n" +      
        ": play ( -- ) maxRange 1 - random 1        \n" +  
        "       dup hexesPerRange * 1 - random      \n" +
        "       swap shoot!                         \n" +
        "       move!                               \n" +
        "       -4 turn!                            \n" +  
        "       move!                               \n" +
        "       .\"SCOUT\" .\"SENT A MES\" send! ;  \n";

    
    CountDownLatch latch = new CountDownLatch(1);

    // Build the script
    MockController mockController = new MockController(RobotType.SCOUT, latch);
    ScriptController controller = new ScriptController(mockController);
    ScriptData data = controller.build("C2", "Simple", script);
    controller.execute(data, mockController);
    
    // Wait for the script to finish executing in the background thread.
    // The latch is released whenever interrupted or finished is called.
    latch.await();
    
    // Test that the output was as expected
    assertEquals(mockController.getShotCount(), 1);
    assertEquals(mockController.getMoveCount(), 2);
    assertEquals(mockController.getDirection(), 2);
    assertEquals(mockController.getMessages().get(0).getValue(), "SENT A MES");
    assertTrue(mockController.isFinished());
    assertFalse(mockController.isInterrupted());
  }
  
  /**
   * Test that the ScriptController produces the expected output when executing a compiled script.
   * It is done using a moderate script with looping and conditional statements but no nesting.
   */
  @Test
  public void runModerateScript()
      throws UnexpectedTokenException, UnknownWordException, InterruptedException {
    String script =  
        ": maxRange 3 ;                             \n" +               
        ": hexesPerRange 6 ;                        \n" +      
        ": play ( -- ) maxRange 1 - random 1        \n" +  
        "       dup hexesPerRange * 1 - random      \n" +
        "       swap shoot!                         \n" +
        "       0 4 = if                            \n" +
        "           -4 turn!                        \n" +  
        "       else                                \n" +  
        "           1 turn!                         \n" +  
        "           1 1 shoot!                      \n" +  
        "       then                                \n" + 
        "       5 0 do                              \n" +  
        "           .\"TANK\" I send!               \n" +  
        "       loop                                \n" +  
        "       move!                               \n" +
        "       .\"SNIPER\" .\"SENT A MES\" send! ; \n";

    
    CountDownLatch latch = new CountDownLatch(1);

    // Build the script
    MockController mockController = new MockController(RobotType.SCOUT, latch);
    ScriptController controller = new ScriptController(mockController);
    ScriptData data = controller.build("C2", "Moderate", script);
    controller.execute(data, mockController);
    
    // Wait for the script to finish executing in the background thread.
    // The latch is released whenever interrupted or finished is called.
    latch.await();
    
    // Test that the output was as expected
    assertEquals(mockController.getShotCount(), 2);
    assertEquals(mockController.getMoveCount(), 1);
    assertEquals(mockController.getDirection(), 1);
    for (int i = 0; i <= 5; i++) {
      assertEquals(mockController.getMessages().get(i).getValue(), i);
    }
    assertEquals(mockController.getMessages().get(6).getValue(), "SENT A MES");
    assertTrue(mockController.isFinished());
    assertFalse(mockController.isInterrupted());
  }
  
  
  /**
   * Test that the ScriptController produces the expected output when executing a compiled script.
   * It is done using a complex script with looping, conditional statements and nesting.
   */
  @Test
  public void runComplexScript() 
    throws UnexpectedTokenException, UnknownWordException, InterruptedException {
    String script =  
        ": destroy!                 \n" +               
        "    scan!                  \n" +      
        "    dup 0 > if             \n" +
        "        dup 1 -            \n" +
        "        0 do               \n" + 
        "            I identify!    \n" + 
        "            team <> if     \n" +
        "                shoot!     \n" +
        "                leave      \n" +
        "            else           \n" +
        "            then           \n" +
        "        loop               \n" +
        "    else                   \n" +
        "    then ;                 \n" +
        ": play ( -- )              \n" +      
        "    begin                  \n" +  
        "        destroy!           \n" +
        "        2 random 1 - turn! \n" +
        "        move!              \n" +
        "        movesLeft 0 =      \n" +  
        "    until                  \n" + 
        "    destroy! ;             \n";
    
    CountDownLatch latch = new CountDownLatch(1);

    // Build the script
    MockController mockController = new MockController(RobotType.SCOUT, latch);
    ScriptController controller = new ScriptController(mockController);
    ScriptData data = controller.build("C2", "Complex", script);
    controller.execute(data, mockController);
    
    // Wait for the script to finish executing in the background thread.
    // The latch is released whenever interrupted or finished is called.
    latch.await();
    
    // Test that the output was as expected
    assertEquals(mockController.getShotCount(), 0);
    assertEquals(mockController.getMoveCount(), 1);
    assertEquals(mockController.movesLeft(), 2);
    assertEquals(mockController.getScanCount(), 2);
    assertTrue(mockController.isFinished());
    assertFalse(mockController.isInterrupted());
  }
}

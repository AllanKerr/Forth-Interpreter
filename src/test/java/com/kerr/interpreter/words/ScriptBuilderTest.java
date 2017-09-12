package com.kerr.interpreter.words;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.kerr.interpreter.controller.UnexpectedTokenException;
import com.kerr.interpreter.controller.UnknownWordException;
import com.kerr.interpreter.model.ScriptData;
import com.kerr.interpreter.controller.ScriptController;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is responsible for testing the ScriptController's ability to build scripts that can
 * then be executed from raw text. Three tests are performed on scripts with simple, moderate, and
 * complex complexities defined based on nesting and word type.
 * 
 * @author allankerr
 *
 */
public class ScriptBuilderTest {

  private ScriptController controller;
  
  @Before
  public void setup() {
    controller = new ScriptController();
  }
  
  /**
   * This test is to test that the ScriptController creates the expected word-tree structure when
   * compiling a simple script including loops, if-statements, and user-defined words. The
   * structure of the script is checked by manually checking the class types of the resulting words.
   * The correct structure was determined via running through the script on paper.
   * 
   */
  @Test
  public void buildSimpleScript() throws UnexpectedTokenException, UnknownWordException {
    String script =  
        ": maxRange 3 ;                             \n" +               
        ": hexesPerRange 6 ;                        \n" +      
        ": play ( -- ) maxRange 1 - random 1        \n" +  
        "       dup hexesPerRange * 1 - random      \n" +
        "       swap shoot!                         \n" +
        "       move!                               \n" +
        "       turn!                               \n" +  
        "       move! ;                             \n";
    
    // Build the script
    ScriptData data = controller.build("C2", "Simple", script);
    MultiWord base = (MultiWord) data.getPlayWord();  
    
    assertMultiWord(base, 
        MultiWord.class, 
        LiteralWord.class, 
        SubtractWord.class, 
        RandomWord.class, 
        LiteralWord.class,
        DupWord.class,
        MultiWord.class,
        MultiplyWord.class,
        LiteralWord.class,
        SubtractWord.class,
        RandomWord.class,
        SwapWord.class,
        ShootWord.class,
        MoveWord.class,
        TurnWord.class,
        MoveWord.class);
    
    // Verify the maxRange user-defined function. 
    // User-defined functions are compiled inline.
    MultiWord maxRange = (MultiWord)base.words.get(0);
    assertMultiWord(maxRange, LiteralWord.class);
    
    // Verify the hexesPerRange user-defined function. 
    // User-defined functions are compiled inline.
    MultiWord hexesPerRange = (MultiWord)base.words.get(6);
    assertMultiWord(hexesPerRange, LiteralWord.class);
  }
  
  /**
   * This test is to test that the ScriptController creates the expected word-tree structure when
   * compiling a moderate script including variables, if-statements, and user-defined words. The
   * structure of the script is checked by manually checking the class types of the resulting words.
   * The correct structure was determined via running through the script on paper.
   * 
   */
  @Test
  public void buildModerateScript() throws UnexpectedTokenException, UnknownWordException {
    String script = 
        "variable canShoot ;                        \n" + 
        ": canShoot? canShoot ? ;                   \n" +
        ": maxRange 3 ;                             \n" + 
        ": hexesPerRange 6 ;                        \n" + 
        ": play ( -- ) maxRange 1 - random 1 +      \n" + 
        "       dup hexesPerRange * 1 - random      \n" + 
        "       swap canShoot? shoot!               \n" + 
        "       0 1 random <> if                    \n" + 
        "           .\"print this message\" .       \n" + 
        "       else                                \n" + 
        "           .\"print other message\" .      \n" + 
        "       then ;                              \n";
    
    // Build the script
    ScriptData data = controller.build("C2", "Moderate", script);
    MultiWord base = (MultiWord) data.getPlayWord();  
    
    assertMultiWord(base, 
        MultiWord.class, 
        LiteralWord.class, 
        SubtractWord.class, 
        RandomWord.class, 
        LiteralWord.class,
        PlusWord.class,
        DupWord.class,
        MultiWord.class,
        MultiplyWord.class,
        LiteralWord.class,
        SubtractWord.class,
        RandomWord.class,
        SwapWord.class,
        MultiWord.class,
        ShootWord.class,
        LiteralWord.class,
        LiteralWord.class,
        RandomWord.class,
        NotEqualsWord.class,
        IfWord.class);

    // Verify the maxRange user-defined function. 
    // User-defined functions are compiled inline.
    MultiWord maxRange = (MultiWord)base.words.get(0);
    assertMultiWord(maxRange, LiteralWord.class);    
    
    // Verify the hexesPerRange user-defined function. 
    // User-defined functions are compiled inline.
    MultiWord hexesPerRange = (MultiWord)base.words.get(7);
    assertMultiWord(hexesPerRange, LiteralWord.class);
    
    // Verify the canShoot? user-defined function. 
    // User-defined functions are compiled inline.
    MultiWord canShoot = (MultiWord)base.words.get(13);
    assertMultiWord(canShoot, 
        LiteralWord.class,
        GetValueWord.class);
    
    // Verify the if-statement
    IfWord ifWord = (IfWord)base.words.get(19);
    assertMultiWord(ifWord.trueCase, 
        LiteralWord.class,
        PrintWord.class);
    assertMultiWord(ifWord.falseCase, 
        LiteralWord.class,
        PrintWord.class);
  }
  
  /**
   * This test is to test that the ScriptController creates the expected word-tree structure when
   * compiling a complex script including loops, if-statements, and user-defined words. The
   * structure of the script is checked by manually checking the class types of the resulting words.
   * The correct structure was determined via running through the script on paper.
   * 
   */
  @Test
  public void buildComplexScript() throws UnexpectedTokenException, UnknownWordException {
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

   // Build the script
   ScriptData data = controller.build("C2", "Complex", script);
   MultiWord base = (MultiWord) data.getPlayWord();  

   // Verify that the loop was built into the expected word-tree format.
   GuardedLoopWord loop = (GuardedLoopWord) base.words.get(0);   
   {
     assertMultiWord(loop.body, 
         MultiWord.class, 
         LiteralWord.class, 
         RandomWord.class, 
         LiteralWord.class, 
         SubtractWord.class,
         TurnWord.class,
         MoveWord.class,
         MovesLeftWord.class,
         LiteralWord.class,
         EqualsWord.class);

     // Verify that the scan and shoot was properly built inside the loop boody
     MultiWord scanAndShoot = (MultiWord) loop.body.words.get(0); 
     {
       assertMultiWord(scanAndShoot,
           ScanWord.class,
           DupWord.class,
           LiteralWord.class,
           GreaterThanWord.class,
           IfWord.class);
       IfWord ifWord = (IfWord) scanAndShoot.words.get(4);
       {
         MultiWord trueCase = ifWord.trueCase;
         assertMultiWord(trueCase, 
             DupWord.class, 
             LiteralWord.class, 
             SubtractWord.class, 
             LiteralWord.class);
         CountedLoopWord loopWord = (CountedLoopWord) trueCase.words.get(4);
         {
           assertMultiWord(loopWord.body, 
               LiteralWord.class, 
               IdentifyWord.class, 
               TeamWord.class, 
               NotEqualsWord.class,
               IfWord.class);

           IfWord innerIfWord = (IfWord)loopWord.body.words.get(4);
           {
             MultiWord innerTrueCase = innerIfWord.trueCase;
             assertMultiWord(innerTrueCase, 
                 ShootWord.class, 
                 LeaveWord.class);
           }
           assertEmpty(innerIfWord.falseCase);
         }
         assertEmpty(ifWord.falseCase);
       }
     }

     // Verify that the final scan and shoot was properly built
     MultiWord finalScanAndShoot = (MultiWord) base.words.get(1); 
     {
       assertMultiWord(finalScanAndShoot,
           ScanWord.class,
           DupWord.class,
           LiteralWord.class,
           GreaterThanWord.class,
           IfWord.class);
       IfWord ifWord = (IfWord) finalScanAndShoot.words.get(4);
       {
         MultiWord trueCase = ifWord.trueCase;
         assertMultiWord(trueCase, 
             DupWord.class, 
             LiteralWord.class, 
             SubtractWord.class, 
             LiteralWord.class);
         CountedLoopWord loopWord = (CountedLoopWord) trueCase.words.get(4);
         {
           assertMultiWord(loopWord.body, 
               LiteralWord.class, 
               IdentifyWord.class, 
               TeamWord.class, 
               NotEqualsWord.class,
               IfWord.class);

           IfWord innerIfWord = (IfWord)loopWord.body.words.get(4);
           {
             MultiWord innerTrueCase = innerIfWord.trueCase;
             assertMultiWord(innerTrueCase, 
                 ShootWord.class, 
                 LeaveWord.class);
           }
           assertEmpty(innerIfWord.falseCase);
         }
         assertEmpty(ifWord.falseCase);
       }
     }
   }
  }

  /**
   * Asserts that the multiword's body is empty.
   * 
   * @param word
   */
  private void assertEmpty(MultiWord word) {
    try {
      word.words.get(0);
      fail();
    } catch (IndexOutOfBoundsException ex) {}
  }

  /**
   * Asserts that the multi-word contains the expected classes in its body.
   * 
   * @param body The loop-word to be tested for correct structure.
   * @param classes The list of classes expected to be found in the multi-word.
   */
  private void assertMultiWord(MultiWord body, Class<?>... classes) {
    int i = 0;
    for (Class<?> aClass : classes) {
      Word word = body.words.get(i);
      assertEquals(word.getClass(), aClass);
      i += 1;
    }
  }
}

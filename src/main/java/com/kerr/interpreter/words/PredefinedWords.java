package com.kerr.interpreter.words;

import java.util.HashMap;

/**
 * Singleton class for managing all predefined words. This class registers all predefined words
 * allowing for the script building to lookup predefined words based on their name. By using a
 * singleton, there is only ever a single instance of any predefined word.
 * 
 * @author allankerr
 *
 */
public class PredefinedWords {

  private static PredefinedWords instance = null;

  public static PredefinedWords getInstance() {
    if (instance == null) {
      instance = new PredefinedWords();
    }
    return instance;
  }

  private HashMap<String, PredefinedWord> words;

  /**
   * Determines if there is a predefined word with the specified name.
   * 
   * @param name The name of the predefined word.
   * @return True if there is a predefined word with the name; otherwise, false.
   */
  public boolean hasWord(String name) {
    return words.containsKey(name);
  }

  /**
   * Gets the predefined word with the specified name.
   * 
   * @param name The name of the predefined word.
   * @return The predefined word's executable word subclass.
   */
  public Word getWord(String name) {
    return words.get(name);
  }

  /**
   * Private constructor that constructs all the predefined word instances and registers them for
   * lookup.
   */
  private PredefinedWords() {
    words = new HashMap<String, PredefinedWord>();

    // Stack Words
    register(new DropWord());
    register(new DupWord());
    register(new SwapWord());
    register(new RotWord());
    register(new PopWord());

    // Arithmetic Words
    register(new PlusWord());
    register(new SubtractWord());
    register(new MultiplyWord());
    register(new ModWord());

    // Comparison Words
    register(new LessThanWord());
    register(new LessThanOrEqualWord());
    register(new EqualsWord());
    register(new NotEqualsWord());
    register(new GreaterThanWord());
    register(new GreaterThanOrEqualWord());

    // Logic Words
    register(new AndWord());
    register(new OrWord());
    register(new InvertWord());

    // Variable Words
    register(new StoreValueWord());
    register(new GetValueWord());

    // Utility Words
    register(new PrintWord());
    register(new RandomWord());

    // Robot Words
    register(new ShootWord());
    register(new MoveWord());
    register(new OtherMoveWord());
    register(new TurnWord());
    register(new CheckWord());
    register(new ScanWord());
    register(new IdentifyWord());

    // Robot Status Words
    register(new AttackWord());
    register(new HealthLeftWord());
    register(new HealthWord());
    register(new MovesLeftWord());
    register(new MovesWord());
    register(new RangeWord());
    register(new TeamWord());
    register(new TypeWord());

    // Mail Words
    register(new SendWord());
    register(new ReceiveWord());
    register(new MessageWord());

    // Loop Words
    register(new LeaveWord());
  }

  /**
   * Add a predefined word to the words map for easy lookup by name.
   * 
   * @param word The predefined word to register.
   */
  private void register(PredefinedWord word) {
    words.put(word.getName(), word);
  }
}

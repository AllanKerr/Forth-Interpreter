package com.kerr.interpreter.words;

import com.kerr.interpreter.controller.ScriptDataSource;
import com.kerr.interpreter.controller.ScriptListener;
import com.kerr.interpreter.model.ScriptData;
import java.util.LinkedList;
import java.util.List;

/**
 * Acts as a container for multiple executable words. When a multi-word is executed, all the words
 * within it are executed sequentially.
 * 
 * @author allankerr
 *
 */
public class MultiWord extends Word {

  /**
   * The list of words to be executed in sequential order.
   */
  protected List<Word> words;

  @Override
  public String toString() {
    return indentedToString(0);
  }

  @Override
  protected String indentedToString(int indents) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append("MultiWord[] {\n");
    for (Word word : words) {
      builder.append(word.indentedToString(indents + 1) + "\n");
    }
    for (int i = 0; i < indents; i++) {
      builder.append("\t");
    }
    builder.append("}");
    if (indents > 0) {
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Constructs a new multi-word with a list of words to execute.
   * 
   * @param words
   */
  public MultiWord(List<Word> words) {
    super();
    this.words = words;
  }

  /**
   * Constructs a new multi-word by appending a word to the start of an existing multi-word.
   * 
   * @param start The word to be appended to the start.
   * @param word The existing multi-word that the string is appended to.
   */
  public MultiWord(Word start, MultiWord word) {
    super();
    this.words = new LinkedList<Word>(word.words);
    this.words.add(0, start);
  }

  /**
   * Executes the words in sequential order.
   */
  @Override
  public void execute(ScriptData data, ScriptListener listener, ScriptDataSource dataSource) {
    for (Word word : words) {
      word.execute(data, listener, dataSource);
    }
  }
}

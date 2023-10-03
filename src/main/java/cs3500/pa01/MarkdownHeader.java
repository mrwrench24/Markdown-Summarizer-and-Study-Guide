package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a header in a Markdown file. Has a level of importance (denoted by
 * number of # signs which precede the name of the header) between 1 and 4, 1 being the highest.
 * Contains sub-items directly below it - whether that be other headers with a smaller
 * level of importance or MarkdownPoints directly below this header in the file.
 */
public class MarkdownHeader implements IMarkdownItem {
  String name;
  int headingLevel;

  /**
   * Takes in a String (in Markdown form) and determines the header's name and level of importance
   * appropriately.
   *
   * @param phrase - A Markdown header (in plain text). Must start with # sign(s) and contain at
   *               least one character after header marking.
   */
  MarkdownHeader(String phrase) {
    this.name = this.getName(phrase);
    this.headingLevel = this.getLevel(phrase);
  }

  /**
   * Extracts the name from a Markdown header.
   *
   * @param phrase - a Markdown header that starts with # sign(s) and contains at least one
   *               character
   *               after header marking.
   * @return - the name associated with supplied Markdown header.
   */
  private String getName(String phrase) {
    int startFrom = phrase.lastIndexOf("#");

    if (startFrom + 1 == phrase.length()) {
      throw new IllegalArgumentException("Supplied phrase does not contain anything after # signs");
    }

    return phrase.substring(startFrom + 1);
  }

  /**
   * Returns the level of importance associated with the header, 1 being the highest
   * and 4 being the lowest.
   *
   * @param phrase - A markdown header in plain text with between 1 and 4 (inclusive) # signs
   *               at the beginning and at least one character thereafter.
   * @return - the level of importance associated with supplied header.
   */
  private int getLevel(String phrase) {
    int value = phrase.lastIndexOf("#");

    // Must have between 1 and 4 #'s only in the beginning of the header.
    if (value < 0 || value > 3) {
      throw new IllegalArgumentException("Incorrectly formatted header supplied, # found at index "
          + value);
    }

    // add 1 so we get the actual count/number of # signs
    return value + 1;
  }

  /**
   * Represents this header as a String, either in markdown form (include # signs) or not.
   *
   * @param inMarkdown - whether this MarkdownItem should be return in its
   *                   markdown form or not.
   * @return - this header as a String, either in Markdown form or not.
   */
  @Override
  public String toString(boolean inMarkdown) {
    // should have a line before it no matter what
    String result = "\n";

    if (inMarkdown) {
      for (int i = 0; i < headingLevel; i++) {
        result += "#";
      }
    }

    // name already has a space before if needed
    result += name + "\n";

    return result;
  }

  /**
   * Returns this header, as it is considered important (for summarizing). In Markdown form.
   *
   * @return - the important points nested in this header
   */
  @Override
  public String getImportant() {
    String result = "\n";

    for (int i = 0; i < headingLevel; i++) {
      result += "#";
    }

    result += name + "\n";

    return result;
  }
}

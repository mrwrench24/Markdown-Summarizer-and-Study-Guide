package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a bullet point in markdown. Each bullet point contains a certain amount of text in
 * markdown. Words which are and aren't important ( denoted by [[ ]] ) are separated and
 * constructed as separate MarkdownPhrases
 */
public class MarkdownPoint implements IMarkdownItem {
  ArrayList<MarkdownPhrase> phrase;

  /**
   * Convenience constructor - takes in a full String and formats it appropriately
   * into MarkdownPhrases
   *
   * @param content - the words this MarkdownPoint represents
   */
  MarkdownPoint(String content) {
    this.phrase = this.asPhrases(content);
  }

  /**
   * Default constructor to create a MarkdownPoint.
   *
   * @param content - The MarkdownPhrases that compose this MarkdownPoint
   */
  MarkdownPoint(ArrayList<MarkdownPhrase> content) {
    this.phrase = content;
  }

  /**
   * Takes in given String and returns a list of MarkdownPhrases that represent it appropriately.
   *
   * @param toFormat - The String we want to transform into one (or more) MarkdownPhrases
   * @return - a list of MarkdownPhrases that this String is represented by.
   */
  private ArrayList<MarkdownPhrase> asPhrases(String toFormat) {
    ArrayList<MarkdownPhrase> result = new ArrayList<MarkdownPhrase>();
    int originIndex = 0;

    while (originIndex < toFormat.length()) {
      String remainingText = toFormat.substring(originIndex);
      int nextStart = remainingText.indexOf("[[");
      int nextEnd = remainingText.indexOf("]]");

      // if neither was found, just keep the rest of the text and move on
      if (nextStart == -1 && nextEnd == -1) {
        result.add(new MarkdownPhrase(remainingText));
        originIndex = toFormat.length();
      } else if (nextStart != -1 && nextEnd != -1 && nextStart < nextEnd) {
        String beforeStart = remainingText.substring(0, nextStart);
        String afterStart = remainingText.substring(nextStart, nextEnd + 2);

        result.add(new MarkdownPhrase(beforeStart));
        result.add(new MarkdownPhrase(afterStart));

        originIndex += nextEnd + 2;
      } else {
        throw new IllegalArgumentException("Found important phrase without proper open and close");
      }
    }

    return result;
  }

  /**
   * Represents this bullet point as a string. Formatted in markdown or not based on supplied
   * boolean value.
   *
   * @param inMarkdown - Whether this point should be represented in markdown (include [[ ]])
   * @return - This bullet point as a string, either in markdown form or not.
   */
  public String toString(boolean inMarkdown) {
    String result = "- ";

    for (MarkdownPhrase words : phrase) {
      result += words.toString(inMarkdown);
    }

    return result + "\n";
  }

  /**
   * Extracts a list of MarkdownItems with only important phrases represented. Goes through all
   * of this point's text and extracts each phrase marked important (if any). Each important
   * phrase then becomes part of a newly constructed bullet point that holds only that important
   * phrase.
   *
   * @return - A list of MarkdownItems with only important phrases contained.
   */
  @Override
  public String getImportant() {
    String message = "";
    ArrayList<MarkdownPoint> result = new ArrayList<MarkdownPoint>();
    ArrayList<MarkdownPhrase> importantPhrases = new ArrayList<MarkdownPhrase>();

    // for every phrase in this point, get a list of the important ones
    for (MarkdownPhrase words : phrase) {
      if (words.important) {
        // adds a new version of it but JUST the plain text, no brackets!!
        MarkdownPhrase toAdd = new MarkdownPhrase(words.toString(false));
        importantPhrases.add(toAdd);
      }
    }

    // for each of the important ones, build a new point containing just that phrase
    for (MarkdownPhrase impPhrase : importantPhrases) {
      result.add(new MarkdownPoint(impPhrase.toString(true)));
    }

    // for each of these new points, print it out / add it to result string
    for (MarkdownPoint bulletPoint : result) {
      message += bulletPoint.toString(true);
    }

    return message;
  }
}

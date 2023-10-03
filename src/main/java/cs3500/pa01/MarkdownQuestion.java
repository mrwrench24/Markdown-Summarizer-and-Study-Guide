package cs3500.pa01;

import static cs3500.pa01.Difficulty.HARD;

/**
 * Represents a question written in Markdown form.
 */
public class MarkdownQuestion {
  String question;
  String answer;
  Difficulty originalDifficulty;
  Difficulty newDifficulty;

  /**
   * Builds a new MarkdownQuestion. originalDifficulty automatically set to Hard, newDifficulty
   * automatically set to null.
   *
   * @param question - the question asked, comes before the ":::"
   * @param answer   - the answer to this question, comes after the ":::"
   */
  MarkdownQuestion(String question, String answer) {
    this.question = question;
    this.answer = answer;
    this.originalDifficulty = HARD;
    this.newDifficulty = HARD;
  }

  /**
   * Builds a new MarkdownQuestion using given question and answer string. Third string should come
   * from reading a .sr file, and should correspond to one of the difficulty enums (for example,
   * "H" for hard or "E" for easy.). Original difficulty set to be corresponding difficulty
   * enumeration, or throws exception otherwise.
   *
   * @param question - the question asked, comes before the ":::"
   * @param answer   - the answer to this question, comes after the ":::"
   */
  MarkdownQuestion(String question, String answer, String difficulty) {
    this.question = question;
    this.answer = answer;
    this.originalDifficulty = Difficulty.getCorresponding(difficulty);
    this.newDifficulty = this.originalDifficulty;
  }

  /**
   * Builds a new MarkdownQuestion by taking in a given phrase in Markdown form, which
   * should start immediately with the "[[" denoting an important phrase, include a ":::"
   * denoting a question specifically, and end with a "]]" denoting the end of the content.
   *
   * @param phrase - A phrase in markdown form representing a question..
   */
  MarkdownQuestion(String phrase) {
    int questionIndex = phrase.indexOf(":::");
    int endingIndex = phrase.indexOf("]]");

    this.question = phrase.substring(2, questionIndex);
    this.answer = phrase.substring(questionIndex + 3, endingIndex);

    this.originalDifficulty = HARD;
    this.newDifficulty = HARD;
  }

  /**
   * Returns a String representation of this question, either in markdown form or not.
   *
   * @param inMarkdown - whether this MarkdownItem should be return in its
   *                   markdown form or not.
   * @return - A String representation of this question, either in markdown form or not
   */
  public String toString(boolean inMarkdown) {
    if (inMarkdown) {
      return "[[" + question + ":::" + answer + "]]"
          + Difficulty.abbreviate(this.originalDifficulty);
    } else {
      return question + " " + answer;
    }
  }

  /**
   * Returns the important content associated with this Markdown Question. Since questions
   * will not be included in the final summarized file, an empty string is returned.
   *
   * @return - an empty String as questions are not considered important content.
   */
  public String getImportant() {
    return "";
  }
}

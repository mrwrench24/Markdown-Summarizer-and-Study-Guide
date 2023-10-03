package cs3500.pa01;

/**
 * Represents the difficulty of a certain question.
 */
public enum Difficulty {
  EASY,
  HARD;

  /**
   * Returns difficulty associated with given single-letter string, intended to be read from a
   * .sr file containing difficulties associated with questions before/after study sessions.
   * (For example, "H" for hard, "E" for easy).
   *
   * @param text - A one-letter, capitalized String that corresponds to a difficulty.
   * @return - Difficulty associated with given String.
   */
  public static Difficulty getCorresponding(String text) {
    if (text.equals(abbreviate(EASY))) {
      return EASY;
    } else if (text.equals(abbreviate(HARD))) {
      return HARD;
    } else {
      throw new IllegalArgumentException("String " + text + " has no corresponding difficulty.");
    }
  }

  /**
   * Returns a String abbreviation to be used in a .sr file to store difficulty data regarding
   * a question.
   *
   * @param givenDifficulty - the difficulty for which we want an abbreviation
   * @return - abbreviation associated with given difficulty.
   */
  public static String abbreviate(Difficulty givenDifficulty) {
    return givenDifficulty.toString().substring(0, 1);
  }
}

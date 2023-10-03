package cs3500.pa01;

/**
 * Represents a statistic that is kept track of during the StudySession.
 */
public class Statistic {
  private String summary;
  private int statVal;

  /**
   * Builds a new Statistic, containing what the statistic is and what the value is.
   *
   * @param content - a String description of the statistic.
   * @param value   - the value the statistic has.
   */
  Statistic(String content, int value) {
    this.summary = content;
    this.statVal = value;
  }

  /**
   * Summarizes this Statistic in a String.
   *
   * @return - a summary of this statistic in a String.
   */
  public String toString() {
    return summary + ": " + statVal;
  }
}

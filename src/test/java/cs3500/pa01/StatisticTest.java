package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Statistic class.
 */
public class StatisticTest {
  Statistic testMean = new Statistic("Mean", 15);
  Statistic testMedian = new Statistic("Median", 14);
  Statistic testMode = new Statistic("Mode", 11);

  /**
   * Tests Statistic's toString method
   */
  @Test
  public void testToString() {
    assertEquals("Mean: 15", testMean.toString());
    assertEquals("Median: 14", testMedian.toString());
    assertEquals("Mode: 11", testMode.toString());
  }
}

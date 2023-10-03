package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the Difficulty enumeration
 */
public class DifficultyTest {

  /**
   * Tests the getCorresponding method for Difficulty enumeration
   */
  @Test
  public void testGetCorresponding() {
    assertEquals("H", Difficulty.HARD.toString().substring(0, 1));
    assertEquals(Difficulty.HARD, Difficulty.getCorresponding("H"));
    assertEquals(Difficulty.EASY, Difficulty.getCorresponding("E"));

    assertThrows(
        IllegalArgumentException.class,
        () -> Difficulty.getCorresponding("A"));

    assertThrows(
        IllegalArgumentException.class,
        () -> Difficulty.getCorresponding("h"));

    assertThrows(
        IllegalArgumentException.class,
        () -> Difficulty.getCorresponding("e"));
  }

  /**
   * Tests Difficulty's abbreviate method
   */
  @Test
  public void testAbbreviate() {
    assertEquals("E", Difficulty.abbreviate(Difficulty.EASY));
    assertEquals("H", Difficulty.abbreviate(Difficulty.HARD));
  }
}

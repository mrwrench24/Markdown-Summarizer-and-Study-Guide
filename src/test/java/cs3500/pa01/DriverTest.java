package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver class
 */
class DriverTest {
  String[] twoArgsArr = new String[] {"hello", "there"};

  String[] invalidFlag = new String[] {"src/test/resources/SampleInputs", "DOJACAT",
      "src/test/java/resources/SampleInputs/Summarized"};

  // the last path doesn't exist
  String[] lastInvalid = new String[] {"src/test/resources/SampleInputs", "filename",
      "src/test/java/resources/SampleInputs/Summarized"};

  String[] nameArr = new String[] {"src/test/resources", "filename",
      "src/test/resources/SampleInputs/Summarized"};

  String[] modifiedArr = new String[] {"src/test/resources", "modified",
      "src/test/resources/SampleInputs/Summarized"};

  String[] createdArr = new String[] {"src/test/resources", "created",
      "src/test/resources/SampleInputs/Summarized"};

  /**
   * Ensures that exceptions are thrown when an invalid number of args is supplied
   * or the args supplied aren't necessarily correct.
   */
  @Test
  public void testInvalidArgs() {
    // wrong number of args
    assertThrows(
        IllegalArgumentException.class,
        () -> Driver.main(twoArgsArr)
    );

    assertThrows(
        IllegalArgumentException.class,
        () -> Driver.main(invalidFlag));

    // first arg is not a valid path
    assertThrows(
        RuntimeException.class,
        () -> Driver.main(lastInvalid)
    );
  }

  /**
   * Tests Driver's main method
   */
  @Test
  public void testMain() {
    Scanner nameScanner;
    // using names because this stays the same locally and on github
    Driver.main(nameArr);
    try {
      nameScanner =
          new Scanner(new File("src/test/resources/SampleInputs/Summarized.md"));
    } catch (IOException e) {
      throw new RuntimeException("Couldn't scan file due to error: " + e);
    }

    nameScanner.nextLine();
    assertEquals("## This file only contains headers.", nameScanner.nextLine());
    assertEquals("", nameScanner.nextLine());
    assertEquals("## To keep it simple, these headers are all going to be of the same importance.",
        nameScanner.nextLine());
  }

  /**
   * Ensures that the Driver will not successfully summarize files in an invalid directory
   */
  @Test
  public void testInvalidSummarizing() {
    String[] invalidArr = new String[] {"src/test/SampleInputs", "filename",
        "src/test/resources/SampleInputs/YouWon'tUseThisAnyways.md"};

    assertThrows(
        RuntimeException.class,
        () -> Driver.main(invalidArr)
    );
  }

  /**
   * Ensures that running main with each of the valid flag names doesn't throw exception
   */
  @Test
  public void testValidFlags() {
    // putting in each of the valid flag names shouldn't throw an exception
    assertDoesNotThrow(() -> Driver.main(nameArr));
    assertDoesNotThrow(() -> Driver.main(modifiedArr));
    assertDoesNotThrow(() -> Driver.main(createdArr));
  }
}
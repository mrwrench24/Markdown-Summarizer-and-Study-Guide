package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Tests the userInputHandler class and its nextInput method
 */
public class UserInputHandlerTest {
  File inputFile = new File("src/test/resources/SampleUserInput.txt");
  UserInputHandler basicHandler = new UserInputHandler(inputFile);

  /**
   * Tests the next input method, reading the numbers from the file as if they
   * were user input.
   */
  @Test
  public void testNextInput() {
    // file is 3, 4, 5, 8
    assertThrows(
        IllegalArgumentException.class,
        () -> basicHandler.nextInput(6, 7));
    assertEquals(4, basicHandler.nextInput(4, 4));
    assertEquals(5, basicHandler.nextInput(4, 6));
    assertThrows(
        IllegalArgumentException.class,
        () -> basicHandler.nextInput(3, 4));
  }

  /**
   * Tests successful/unsuccessful construction of userInputHandlers, should not construct
   * if reading from a file that doesn't exist (for testing)
   */
  @Test
  public void testConstruction() {
    assertDoesNotThrow(() -> new UserInputHandler());
    assertDoesNotThrow(() -> new UserInputHandler(inputFile));

    assertThrows(
        RuntimeException.class,
        () -> new UserInputHandler(new File("/adfa/adsfa/dfahla")));
  }
}

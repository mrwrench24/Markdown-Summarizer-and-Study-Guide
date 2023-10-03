package cs3500.pa01;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents an object responsible for handling and validating user
 * input during a StudySession.
 */
public class UserInputHandler {
  private Scanner inputScanner;

  /**
   * Builds a new input handler, initializes scanner to take input from user
   * through console.
   */
  UserInputHandler() {
    inputScanner = new Scanner(System.in);
  }

  /**
   * Builds a new input handler that reads "input" from a file, used for testing
   *
   * @param toRead - a file to simulate the reading of input from
   */
  UserInputHandler(File toRead) {
    try {
      inputScanner = new Scanner(toRead);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Allows the user to input a number. Throws an exception if input is not in given
   * range (inclusive), but this exception is handled gracefully
   *
   * @return - The input from the user.
   */
  public int nextInput(int minInput, int maxInput) {
    int input = inputScanner.nextInt();
    if (input >= minInput && input <= maxInput) {
      return input;
    } else {
      throw new IllegalArgumentException(
          "User input of " + input + "not between " + minInput + " - " + maxInput);
    }
  }
}

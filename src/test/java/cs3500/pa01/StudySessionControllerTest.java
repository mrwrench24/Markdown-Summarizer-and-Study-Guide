package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the StudySessionController
 */
public class StudySessionControllerTest {
  File controllerQuestions;
  QuestionFile controllerqf;
  StudySessionModel testModel;

  File sampleInputFile = new File("src/test/resources/SampleSessionInput.txt");
  UserInputHandler testHandler;

  StudySessionController testController;

  MarkdownQuestion original1 = new MarkdownQuestion("Hard Question", "Hard", "H");
  MarkdownQuestion original2 = new MarkdownQuestion("Easy Question", "Easy", "E");
  ArrayList<MarkdownQuestion> originalList = new ArrayList<>(Arrays.asList(original1, original2));

  ByteArrayOutputStream consoleContents = new ByteArrayOutputStream();

  /**
   * Reinitializes each of these fields to prevent errors with mutation
   */
  @BeforeEach
  public void initData() {
    controllerQuestions =
        new File("src/test/resources/SampleQuestions/ControllerQuestions.sr");
    controllerqf = new QuestionFile(controllerQuestions);
    testModel = new StudySessionModel(controllerqf);
    testHandler = new UserInputHandler(sampleInputFile);
    testController = new StudySessionController(testModel, testHandler);

    System.setOut(new PrintStream(consoleContents));
  }

  /**
   * Returns the outputStream to be System.out again after tests are complete
   */
  @AfterAll
  public static void reset() {
    System.setOut(System.out);
  }

  /**
   * Tests the first part of a study session - welcoming user, asking how many questions,
   * and the model building up to that many questions
   */
  @Test
  public void testRunSetup() {
    testController.runSetup();

    assertEquals("Welcome to StudySession - 3500 Edition!\n"
        + "Up to how many questions would you like to study today?\n"
        + "Please enter a number: "
        + "Please input between 1 and 2147483647, inclusive"
        + "\nPlease enter a number: ", consoleContents.toString());

    // User inputs "3", only 2 questions in bank...

    assertEquals(Difficulty.HARD, testModel.getQuestion().originalDifficulty);

    testModel.increment();
    assertEquals(Difficulty.EASY, testModel.getQuestion().originalDifficulty);

    testModel.increment();
    assertFalse(testModel.hasQuestions());
  }

  /**
   * Tests a simulated study session where a hard question is then marked as easy.
   */
  @Test
  public void testStudySession() {
    testController.runSession();
    try {
      Scanner sc = new Scanner(controllerQuestions);
      sc.useDelimiter("\n");
      assertEquals("[[Hard Question:::Hard]]E", sc.next());
      assertEquals("[[Easy Question:::Easy]]E", sc.next());
    } catch (IOException e) {
      fail();
    }

    controllerqf.writeToFile(originalList);
  }
}

package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the StudySessionModel
 */
public class StudySessionModelTest {

  File exampleQuestions;
  QuestionFile qf;
  StudySessionModel exampleModel;

  File statsFile;
  QuestionFile statsqf;
  StudySessionModel statsModel;

  MarkdownQuestion statsOriginal1 = new MarkdownQuestion("Hard Question", "Hard", "H");
  MarkdownQuestion statsOriginal2 = new MarkdownQuestion("Easy Question", "Easy", "E");
  ArrayList<MarkdownQuestion> statsOriginal = new ArrayList<>(
      Arrays.asList(statsOriginal1, statsOriginal2));

  /**
   * Resets/re-initializes the fields to prevent issues with mutation.
   */
  @BeforeEach
  public void initData() {
    exampleQuestions = new File("src/test/resources/SampleQuestions/FullQuestions.sr");
    qf = new QuestionFile(exampleQuestions);
    exampleModel = new StudySessionModel(qf);

    statsFile = new File("src/test/resources/SampleQuestions/StatsQuestions.sr");
    statsqf = new QuestionFile(statsFile);
    statsModel = new StudySessionModel(statsqf);
  }

  /**
   * Tests the setup method, and ensures all of the questions (from a bank of nine) are only
   * hard (there are only 6 hard questions available)
   */
  @Test
  public void testSetupHardOnly() {
    exampleModel.setup(6);
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertFalse(exampleModel.hasQuestions());
  }

  /**
   * Tests the setup method, ensuring it works when called wiht a number greater than the bank
   * size and that the questions that come first are hard, then come the easy questions
   */
  @Test
  public void testSetupOverBankSize() {
    exampleModel.setup(100);
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.EASY, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.EASY, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertEquals(Difficulty.EASY, exampleModel.getQuestion().originalDifficulty);

    exampleModel.increment();
    assertFalse(exampleModel.hasQuestions());
  }

  /**
   * Tests the "hasQuestions" method, which returns whether the model has a question for
   * the user to study.
   */
  @Test
  public void testHasQuestions() {
    exampleModel.setup(4);

    assertTrue(exampleModel.hasQuestions());
    exampleModel.increment();

    assertTrue(exampleModel.hasQuestions());
    exampleModel.increment();

    assertTrue(exampleModel.hasQuestions());
    exampleModel.increment();

    assertTrue(exampleModel.hasQuestions());
    exampleModel.increment();

    assertFalse(exampleModel.hasQuestions());
  }

  /**
   * Tests the increment method.
   */
  @Test
  public void testIncrement() {
    exampleModel.setup(2);

    MarkdownQuestion firstQuestion = exampleModel.getQuestion();
    exampleModel.increment();
    MarkdownQuestion secondQuestion = exampleModel.getQuestion();
    assertFalse(firstQuestion.equals(secondQuestion));

    // index is now 2 for a list of size 2, no more questions
    exampleModel.increment();
    assertFalse(exampleModel.hasQuestions());
  }

  /**
   * Tests the markQuestion method.
   */
  @Test
  public void testMarkQuestion() {
    exampleModel.setup(1);

    assertEquals(Difficulty.HARD, exampleModel.getQuestion().newDifficulty);
    exampleModel.markQuestion(Difficulty.HARD);
    assertEquals(Difficulty.HARD, exampleModel.getQuestion().newDifficulty);
    exampleModel.markQuestion(Difficulty.EASY);
    assertEquals(Difficulty.EASY, exampleModel.getQuestion().newDifficulty);
  }

  /**
   * Tests the getAnswer method.
   */
  @Test
  public void testGetAnswer() {
    File testQuestionsFile = new File("src/test/resources/SampleQuestions/TestQuestions.sr");
    QuestionFile getAnswerQuestionFile = new QuestionFile(testQuestionsFile);
    StudySessionModel getAnswerModel = new StudySessionModel(getAnswerQuestionFile);
    getAnswerModel.setup(2);

    // order of these questions is predictable because there is one hard and one easy
    assertEquals("True", getAnswerModel.getAnswer());
    getAnswerModel.increment();
    assertEquals("2", getAnswerModel.getAnswer());
  }

  /**
   * Tests the getStats method / generation of statistics for a SessionModel.
   */
  @Test
  public void testGetStats() {
    statsModel.setup(2);
    // mark first question hard to easy
    statsModel.markQuestion(Difficulty.EASY);

    // mark second question easy to hard
    statsModel.increment();
    statsModel.markQuestion(Difficulty.HARD);

    ArrayList<Statistic> stats = statsModel.getStats();
    assertEquals("Questions in Session: 2", stats.get(0).toString());
    assertEquals("Questions from Easy to Hard: 1", stats.get(1).toString());
    assertEquals("Questions from Hard to Easy: 1", stats.get(2).toString());
    assertEquals("Easy Questions now in Bank: 1", stats.get(3).toString());
    assertEquals("Hard Questions now in Bank: 1", stats.get(4).toString());

    // resets file once tests are complete
    statsqf.writeToFile(statsOriginal);
  }

  /**
   * Tests the endSession method - ensuring questions "difficulty" is overwritten accurately.
   */
  @Test
  public void testEndSession() {
    statsModel.setup(2);

    statsModel.markQuestion(Difficulty.EASY);

    statsModel.increment();
    statsModel.markQuestion(Difficulty.HARD);

    statsModel.endSession();

    try {
      Scanner sc = new Scanner(statsFile);
      sc.useDelimiter("\n");
      assertEquals("[[Hard Question:::Hard]]E", sc.next());
      assertEquals("[[Easy Question:::Easy]]H", sc.next());
    } catch (IOException e) {
      fail();
    }

    // resets file once tests are complete
    statsqf.writeToFile(statsOriginal);
  }
}

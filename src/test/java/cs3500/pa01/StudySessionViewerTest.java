package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests methods in the StudySessionViewer
 */
public class StudySessionViewerTest {

  ByteArrayOutputStream consoleContents = new ByteArrayOutputStream();
  StudySessionViewer viewer = new StudySessionViewer();
  MarkdownQuestion q1 = new MarkdownQuestion("2 + 2 =", "4", "E");
  MarkdownQuestion q2 = new MarkdownQuestion("5 x 5 =", "25", "H");

  /**
   * Modifies the outputStream so it can be tested
   */
  @BeforeEach
  public void setup() {
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
   * Tests the welcome method
   */
  @Test
  public void testWelcome() {
    viewer.welcome();
    assertEquals("Welcome to StudySession - 3500 Edition!\nUp to how many questions"
        + " would you like to study today?\n", consoleContents.toString());
  }

  /**
   * Tests the display method
   */
  @Test
  public void testDisplay() {
    viewer.display(q1);
    assertEquals(
        "Question: 2 + 2 ="
            + "\nEASY question"
            + "\n\n1. Mark as Easy"
            + "\n2. Mark as Hard"
            + "\n3. Reveal Answer\n", consoleContents.toString());

    viewer.display(q2);
    assertEquals(
        "Question: 2 + 2 ="
            + "\nEASY question"
            + "\n\n1. Mark as Easy"
            + "\n2. Mark as Hard"
            + "\n3. Reveal Answer\n"
            + "Question: 5 x 5 ="
            + "\nHARD question"
            + "\n\n1. Mark as Easy"
            + "\n2. Mark as Hard"
            + "\n3. Reveal Answer\n", consoleContents.toString());
  }

  /**
   * Tests the prompt input method
   */
  @Test
  public void testPrompt() {
    viewer.promptInput();
    assertEquals("Please enter a number: ", consoleContents.toString());
  }

  /**
   * Tests the Summarize method
   */
  @Test
  public void testSummarize() {
    ArrayList<Statistic> statList = new ArrayList<>();
    viewer.summarize(statList);

    assertEquals("Great job! Here's some information about your Study Session:\n\n\n"
        + "Thank you for participating in this enrichment activity."
        + "\nRemember, the cake is a LIE. Goodbye!\n", consoleContents.toString());

    statList.add(new Statistic("Mean", 4));
    statList.add(new Statistic("Median", 3));

    viewer.summarize(statList);
    assertEquals("Great job! Here's some information about your Study Session:\n\n\n"
        + "Thank you for participating in this enrichment activity."
        + "\nRemember, the cake is a LIE. Goodbye!\n"
        + "Great job! Here's some information about your Study Session:\n\n\n"
        + "Mean: 4\n"
        + "Median: 3\n"
        + "Thank you for participating in this enrichment activity."
        + "\nRemember, the cake is a LIE. Goodbye!\n", consoleContents.toString());
  }

  /**
   * Tests the scold method
   */
  @Test
  public void testScold() {
    viewer.scold(1, 5);
    assertEquals("Please input between 1 and 5, inclusive\n", consoleContents.toString());

    viewer.scold(3, 4);
    assertEquals("Please input between 1 and 5, inclusive"
        + "\nPlease input between 3 and 4, inclusive\n", consoleContents.toString());

    viewer.scold(4, -1);
    assertEquals("Please input between 1 and 5, inclusive"
        + "\nPlease input between 3 and 4, inclusive"
        + "\nPlease input between 4 and -1, inclusive\n", consoleContents.toString());
  }
}

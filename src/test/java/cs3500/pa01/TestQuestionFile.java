package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests the QuestionFile class
 */
public class TestQuestionFile {

  File validFile = new File("src/test/resources/SampleQuestions/ThursdayQuestions.sr");

  File invalidFile = new File("src/test/resources/SampleInputs/HeadersOnly.md");

  File mutationFile = new File("src/test/resources/SampleQuestions/QuestionsToMutate.sr");

  QuestionFile testFile = new QuestionFile(validFile);

  QuestionFile mutateFile = new QuestionFile(mutationFile);

  MarkdownQuestion mutateOneChanges =
      new MarkdownQuestion("What is the meaning of life", "42", "E");
  MarkdownQuestion mutateTwoChanges =
      new MarkdownQuestion("This is a different question", "yes", "H");
  MarkdownQuestion mutateThreeChanges = new MarkdownQuestion("Portals are", "Blue and Orange", "H");
  MarkdownQuestion mutateFourChanges = new MarkdownQuestion("Atlas and...", "P-Body", "E");
  ArrayList<MarkdownQuestion> changedQuestions = new ArrayList<>(
      Arrays.asList(mutateOneChanges, mutateTwoChanges, mutateThreeChanges, mutateFourChanges));

  MarkdownQuestion mutateOneOriginal =
      new MarkdownQuestion("What is the meaning of life", "42", "H");
  MarkdownQuestion mutateTwoOriginal = new MarkdownQuestion("The cake is a", "lie", "H");
  MarkdownQuestion mutateThreeOriginal =
      new MarkdownQuestion("Portals are", "Blue and Orange", "E");
  MarkdownQuestion mutateFourOriginal = new MarkdownQuestion("Atlas and...", "P-Body", "E");
  ArrayList<MarkdownQuestion> originalQuestions = new ArrayList<>(
      Arrays.asList(mutateOneOriginal, mutateTwoOriginal, mutateThreeOriginal, mutateFourOriginal));

  /**
   * Ensures the constructor will accept valid .sr files and will reject others
   */
  @Test
  public void testConstruction() {
    assertDoesNotThrow(() -> new QuestionFile(validFile));
    assertThrows(
        IllegalArgumentException.class,
        () -> new QuestionFile(invalidFile));
  }

  /**
   * Tests the QuestionFile's ability to read its reference line by line and extract accurate
   * MarkdownQuestion objects
   */
  @Test
  public void testReadFile() {
    ArrayList<MarkdownQuestion> result = testFile.readFile();

    assertEquals("What is 2 + 2", result.get(0).question);
    assertEquals("4", result.get(0).answer);
    assertEquals(Difficulty.EASY, result.get(0).originalDifficulty);
    assertEquals(Difficulty.EASY, result.get(0).originalDifficulty);

    assertEquals("What is 3 x 6 x 8 % 4", result.get(1).question);
    assertEquals("0", result.get(1).answer);
    assertEquals(Difficulty.HARD, result.get(1).originalDifficulty);
    assertEquals(Difficulty.HARD, result.get(1).newDifficulty);

    // This QuestionFile will construct but will not be read/written to successfully...
    File fakeFile = new File("src/test/resources/SampleQuestions/NotAFile.sr");
    QuestionFile nonFileObject = new QuestionFile(fakeFile);

    assertThrows(
        RuntimeException.class,
        () -> nonFileObject.readFile());
  }

  /**
   * Tests the fileWriters ability to write a list of questions to a QuestionFile
   */
  @Test
  public void testWriteQuestions() {

    mutateFile.writeToFile(changedQuestions);

    try {
      Scanner sc = new Scanner(mutationFile);
      sc.useDelimiter("\n");
      assertEquals("[[What is the meaning of life:::42]]E", sc.next());
      assertEquals("[[This is a different question:::yes]]H", sc.next());
      assertEquals("[[Portals are:::Blue and Orange]]H", sc.next());
      assertEquals("[[Atlas and...:::P-Body]]E", sc.next());
    } catch (IOException e) {
      fail();
    }

    mutateFile.writeToFile(originalQuestions);

    try {
      Scanner sc = new Scanner(mutationFile);
      sc.useDelimiter("\n");
      assertEquals("[[What is the meaning of life:::42]]H", sc.next());
      assertEquals("[[The cake is a:::lie]]H", sc.next());
      assertEquals("[[Portals are:::Blue and Orange]]E", sc.next());
      assertEquals("[[Atlas and...:::P-Body]]E", sc.next());
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Ensures that the writeToFile method does not work when given an invalid directory
   */
  @Test
  public void testWriteFailure() {
    // this directory is not valid
    File realFile = new File("src/test/SampleQuestions/QuestionQuestions.sr");
    QuestionFile testqf = new QuestionFile(realFile);

    assertThrows(
        RuntimeException.class,
        () -> testqf.writeToFile(originalQuestions)
    );
  }

}

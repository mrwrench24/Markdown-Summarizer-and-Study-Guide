package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests construction and methods for MarkdownQuestions.
 */
public class QuestionTest {
  MarkdownQuestion question1 = new MarkdownQuestion("What is 2 + 2", "4");
  MarkdownQuestion question2 = new MarkdownQuestion("What is 5 x 2", "10");
  MarkdownQuestion question3 = new MarkdownQuestion("True or False", "True", "E");

  /**
   * Tests the construction of Markdown Questions using just Strings in markdown form
   */
  @Test
  public void testStringConstruction() {
    MarkdownQuestion string1 = new MarkdownQuestion("[[2 + 2:::4]]");
    assertEquals("2 + 2", string1.question);
    assertEquals("4", string1.answer);
    assertEquals(Difficulty.HARD, string1.newDifficulty);
    assertEquals(Difficulty.HARD, string1.originalDifficulty);

    MarkdownQuestion string2 = new MarkdownQuestion("[[5 + 8:::13]]");
    assertEquals("5 + 8", string2.question);
    assertEquals("13", string2.answer);
    assertEquals(Difficulty.HARD, string2.newDifficulty);
    assertEquals(Difficulty.HARD, string2.originalDifficulty);
  }

  /**
   * Tests MarkdownQuestion's toString method
   */
  @Test
  public void testToString() {
    assertEquals("[[What is 2 + 2:::4]]H", question1.toString(true));
    assertEquals("What is 2 + 2 4", question1.toString(false));

    assertEquals("[[What is 5 x 2:::10]]H", question2.toString(true));
    assertEquals("What is 5 x 2 10", question2.toString(false));

    assertEquals("[[True or False:::True]]E", question3.toString(true));
    assertEquals("True or False True", question3.toString(false));
  }

  /**
   * Tests the getImportant method for questions, should always return an empty String.
   */
  @Test
  public void testGetImportant() {
    assertEquals("", question1.getImportant());
    assertEquals("", question2.getImportant());
  }
}

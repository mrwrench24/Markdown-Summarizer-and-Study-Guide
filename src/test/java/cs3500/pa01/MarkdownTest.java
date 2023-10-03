package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Markdown class's creation and its methods.
 */
public class MarkdownTest {
  static final String SAMPLE_DIRECTORY = "src/test/resources/SampleInputs/";

  // Last modified May 14 at 7:38 AM
  File assignmentNotes = new File(SAMPLE_DIRECTORY + "ProgrammingAssignmentNotes.md");

  BasicFileAttributes assignmentAttributes;
  Markdown assignmentMarkdown;

  File includesQuestions = new File(SAMPLE_DIRECTORY + "QuestionsToRead.md");
  BasicFileAttributes questionAttributes;
  Markdown questionMarkdown;

  /**
   * Sets up a Markdown file and reads its attributes from a File object,
   * throws exception if it does not work
   */
  @BeforeEach
  public void initData() {
    try {
      assignmentAttributes = Files.readAttributes(assignmentNotes.toPath(),
          BasicFileAttributes.class);
      assignmentMarkdown = new Markdown(assignmentNotes, assignmentAttributes);
      questionAttributes =
          Files.readAttributes(includesQuestions.toPath(), BasicFileAttributes.class);
      questionMarkdown = new Markdown(includesQuestions, questionAttributes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests the construction of a Markdown file - namely that the correct # of objects
   * are created from its associated file being analyzed
   */
  @Test
  public void testConstruction() {
    assertEquals(21, assignmentMarkdown.items.size());
  }

  /**
   * Tests the getName method for Markdown objects
   */
  @Test
  public void testGetName() {
    assertEquals("ProgrammingAssignmentNotes.md", assignmentMarkdown.getName());
  }

  /**
   * Tests the getCreationTime method
   */
  @Test
  public void testGetCreationTime() {
    assertEquals(assignmentAttributes.creationTime(), assignmentMarkdown.getCreationTime());
  }

  /**
   * Tests the getModificationTime method
   */
  @Test
  public void testGetModificationTime() {
    assertEquals(assignmentAttributes.lastModifiedTime(), assignmentMarkdown.getModifiedTime());
  }

  /**
   * Tests the summarize method for markdown
   */
  @Test
  public void testSummarize() {
    assertEquals(21, assignmentMarkdown.items.size());

    assignmentMarkdown.summarize();
    assertEquals(15, assignmentMarkdown.items.size());

    assertEquals("\n# Welcome to PA01\n",
        assignmentMarkdown.items.get(0).toString(true));
    assertEquals("\n Welcome to PA01\n",
        assignmentMarkdown.items.get(0).toString(false));

    assertEquals("- You need to summarize markdown files\n",
        assignmentMarkdown.items.get(1).toString(true));
    assertEquals("- You need to summarize markdown files\n",
        assignmentMarkdown.items.get(1).toString(false));

    assertEquals("\n## IntelliJ IDEA\n",
        assignmentMarkdown.items.get(2).toString(true));
    assertEquals("\n IntelliJ IDEA\n",
        assignmentMarkdown.items.get(2).toString(false));

    assertEquals("- use for project development\n",
        assignmentMarkdown.items.get(3).toString(true));
    assertEquals("- use for project development\n",
        assignmentMarkdown.items.get(3).toString(false));
  }

  /**
   * Tests Markdown's getQuestions method
   */
  @Test
  public void testGetQuestions() {
    ArrayList<MarkdownQuestion> questionResult = questionMarkdown.getQuestions();
    assertEquals(3, questionResult.size());
    assertEquals("1 + 1", questionResult.get(0).question);
    assertEquals("There can be up to __ levels of headers", questionResult.get(1).question);
    assertEquals("Your questions can", questionResult.get(2).question);
  }
}

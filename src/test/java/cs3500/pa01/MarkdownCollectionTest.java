package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the MarkdownCollection class
 */
public class MarkdownCollectionTest {
  MarkdownCollection nameCollection = new MarkdownCollection(new NameComparison());
  MarkdownCollection modifiedCollection = new MarkdownCollection(new ModifiedComparison());
  MarkdownCollection creationCollection = new MarkdownCollection(new CreationComparison());

  static final String SAMPLE_DIRECTORY = "src/test/resources/SampleInputs/";

  File assignmentNotes = new File(SAMPLE_DIRECTORY + "ProgrammingAssignmentNotes.md");
  FileTime assignmentCreation = FileTime.from(Instant.parse("2023-05-14T10:30:00Z"));
  FileTime assignmentModification = FileTime.from(Instant.parse("2023-05-14T10:15:32Z"));

  File pointsOnly = new File(SAMPLE_DIRECTORY + "PointsOnly.md");
  FileTime pointsCreation = FileTime.from(Instant.parse("2023-05-14T11:15:00Z"));
  FileTime pointsModification = FileTime.from(Instant.parse("2023-05-15T12:30:32Z"));

  File headersOnly = new File(SAMPLE_DIRECTORY + "HeadersOnly.md");
  FileTime headersCreation = FileTime.from(Instant.parse("2023-05-14T12:32:00Z"));
  FileTime headersModification = FileTime.from(Instant.parse("2023-05-15T12:31:16Z"));

  Markdown assignmentMarkdown =
      new Markdown(assignmentNotes, assignmentCreation, assignmentModification);
  Markdown pointsMarkdown = new Markdown(pointsOnly, pointsCreation, pointsModification);
  Markdown headerMarkdown = new Markdown(headersOnly, headersCreation, headersModification);

  String filesSummarized = "# Welcome to PA01\n- You need to summarize markdown files\n\n"
      + "## IntelliJ IDEA\n- use for project development\n- Make sure you download Java and"
      + " set up the correct distributions\n\n## Course Website\n- There are a lot of resources"
      + " available here\n\n### Programming Assignment Write-Up\n- Examples of Markdown"
      + " Files included here\n\n### Lecture Slides\n\n## Important Dates\n- This project"
      + " is due on Tuesday\n- Submitting on GitHub\n- Submit"
      + " early for Extra Credit\n- submit design the Thursday before project is due\n"
      + "- Some of them might be important\n- testing your code is so important\n\n## This"
      + " file only contains headers.\n\n## To keep it simple, these headers are all going to "
      + "be of the same importance.\n\n## Things will get a lot messier later though, when we"
      + " try and test nesting headers and items.\n";

  File questionsFile = new File(SAMPLE_DIRECTORY + "QuestionsToRead.md");
  MarkdownCollection questionCollection = new MarkdownCollection(new NameComparison());
  BasicFileAttributes questionAttributes;
  Markdown questionMarkdown;

  File megaFile = new File(SAMPLE_DIRECTORY + "MegaMarkdown.md");
  BasicFileAttributes megaAttributes;
  Markdown megaMarkdown;

  /**
   * Reinitializes each of the collections to prevent side effects from mutation, primarily
   * due to the summarize and addMarkdown methods being tested
   */
  @BeforeEach
  public void initData() {
    nameCollection = new MarkdownCollection(new NameComparison());
    modifiedCollection = new MarkdownCollection(new ModifiedComparison());
    creationCollection = new MarkdownCollection(new CreationComparison());

    try {
      questionAttributes = Files.readAttributes(questionsFile.toPath(), BasicFileAttributes.class);
      questionMarkdown = new Markdown(questionsFile, questionAttributes);
      megaAttributes = Files.readAttributes(megaFile.toPath(), BasicFileAttributes.class);
      megaMarkdown = new Markdown(megaFile, megaAttributes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    questionCollection.addMarkdown(questionMarkdown);
    questionCollection.addMarkdown(megaMarkdown);
  }

  /**
   * Tests the addMarkdown method when nameComparison is the sorting method. Ensures
   * list stays sorted.
   */
  @Test
  public void testAddWithName() {
    assertEquals(0, nameCollection.collection.size());

    nameCollection.addMarkdown(assignmentMarkdown);
    assertEquals(assignmentMarkdown, nameCollection.collection.get(0));

    // points, programming
    nameCollection.addMarkdown(pointsMarkdown);
    assertEquals(pointsMarkdown, nameCollection.collection.get(0));
    assertEquals(assignmentMarkdown, nameCollection.collection.get(1));

    // header, points, programming
    nameCollection.addMarkdown(headerMarkdown);
    assertEquals(headerMarkdown, nameCollection.collection.get(0));
    assertEquals(pointsMarkdown, nameCollection.collection.get(1));
    assertEquals(assignmentMarkdown, nameCollection.collection.get(2));
  }

  /**
   * Tests the addMarkdown method when modifiedComparison is the sorting method. Ensures
   * list stays sorted.
   */
  @Test
  public void testAddWithModified() {
    assertEquals(0, modifiedCollection.collection.size());

    modifiedCollection.addMarkdown(assignmentMarkdown);
    assertEquals(assignmentMarkdown, modifiedCollection.collection.get(0));

    // points, programming
    modifiedCollection.addMarkdown(pointsMarkdown);
    assertEquals(assignmentMarkdown, modifiedCollection.collection.get(0));
    assertEquals(pointsMarkdown, modifiedCollection.collection.get(1));

    // header, points, programming
    modifiedCollection.addMarkdown(headerMarkdown);
    assertEquals(assignmentMarkdown, modifiedCollection.collection.get(0));
    assertEquals(pointsMarkdown, modifiedCollection.collection.get(1));
    assertEquals(headerMarkdown, modifiedCollection.collection.get(2));
  }

  /**
   * Tests the addMarkdown method when creationComparison is the sorting method. Ensures
   * list stays sorted.
   */
  @Test
  public void testAddWithCreated() {
    assertEquals(0, creationCollection.collection.size());

    creationCollection.addMarkdown(assignmentMarkdown);
    assertEquals(assignmentMarkdown, creationCollection.collection.get(0));

    // points, programming
    creationCollection.addMarkdown(pointsMarkdown);
    assertEquals(assignmentMarkdown, creationCollection.collection.get(0));
    assertEquals(pointsMarkdown, creationCollection.collection.get(1));

    // header, points, programming
    creationCollection.addMarkdown(headerMarkdown);
    assertEquals(assignmentMarkdown, creationCollection.collection.get(0));
    assertEquals(pointsMarkdown, creationCollection.collection.get(1));
    assertEquals(headerMarkdown, creationCollection.collection.get(2));
  }

  /**
   * Ensures that files are summarized accurately and also tests toString by comparing
   * the output of the summarized file with the above defined (expectecd) result
   */
  @Test
  public void testSummarizeAndToString() {
    // order of creation collection would be assignment, points, header
    creationCollection.addMarkdown(assignmentMarkdown);
    creationCollection.addMarkdown(pointsMarkdown);
    creationCollection.addMarkdown(headerMarkdown);

    creationCollection.summarize();
    assertEquals(filesSummarized, creationCollection.toString());
  }

  /**
   * Tests the Collection's getQuestion's method
   */
  @Test
  public void testGetQuestions() {
    // should have MegaMarkdown.md then QuestionsToRead.md
    ArrayList<MarkdownQuestion> result = questionCollection.getQuestions();
    assertEquals(8, result.size());
    assertEquals("[[This file is being used for:::testing]]H", result.get(0).toString(true));
    assertEquals("[[Let's test a couple of extra...:::questions]]H", result.get(1).toString(true));

    assertEquals("[[There can be up to __ levels of headers:::4]]H", result.get(6).toString(true));
    assertEquals("[[Your questions can:::also be multi-line]]H", result.get(7).toString(true));
  }
}

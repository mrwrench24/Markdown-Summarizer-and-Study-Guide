package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the nameComparison method
 */
public class NameComparisonTest {
  IComparison naming = new NameComparison();

  static final String SAMPLE_DIRECTORY = "src/test/resources/SampleInputs/";

  // Last modified May 14 at 7:38 AM
  File assignmentNotes = new File(SAMPLE_DIRECTORY + "ProgrammingAssignmentNotes.md");

  // Last modified May 14 at 8:18 AM
  File pointsOnly = new File(SAMPLE_DIRECTORY + "PointsOnly.md");

  // Last modified May 14 at 8:46 AM
  File headersOnly = new File(SAMPLE_DIRECTORY + "HeadersOnly.md");

  BasicFileAttributes assignmentAttributes;
  BasicFileAttributes pointsAttributes;
  BasicFileAttributes headersAttributes;

  Markdown assignmentMarkdown;
  Markdown pointsMarkdown;
  Markdown headerMarkdown;

  /**
   * Sets up Markdown files and reads their attributes from a File object,
   * throws exception if it does not work
   */
  @BeforeEach
  public void initData() {
    try {
      assignmentAttributes = Files.readAttributes(assignmentNotes.toPath(),
          BasicFileAttributes.class);
      assignmentMarkdown = new Markdown(assignmentNotes, assignmentAttributes);

      pointsAttributes = Files.readAttributes(pointsOnly.toPath(),
          BasicFileAttributes.class);
      pointsMarkdown = new Markdown(pointsOnly, pointsAttributes);

      headersAttributes = Files.readAttributes(headersOnly.toPath(),
          BasicFileAttributes.class);
      headerMarkdown = new Markdown(headersOnly, headersAttributes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests the compare method
   */
  @Test
  public void testNameComparison() {
    assertTrue(naming.compare(headerMarkdown, assignmentMarkdown));
    assertTrue(naming.compare(pointsMarkdown, assignmentMarkdown));
    assertTrue(naming.compare(headerMarkdown, pointsMarkdown));

    assertTrue(naming.compare(headerMarkdown, headerMarkdown));
    assertTrue(naming.compare(headerMarkdown, headerMarkdown));
    assertTrue(naming.compare(headerMarkdown, headerMarkdown));

    assertFalse(naming.compare(assignmentMarkdown, headerMarkdown));
    assertFalse(naming.compare(assignmentMarkdown, pointsMarkdown));
    assertFalse(naming.compare(pointsMarkdown, headerMarkdown));
  }
}

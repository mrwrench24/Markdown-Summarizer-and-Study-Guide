package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the CreationComparison class
 */
public class CreationComparisonTest {
  IComparison created = new CreationComparison();
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

  /**
   * Tests the compare method
   */
  @Test
  public void testCreationComparison() {
    assertTrue(created.compare(assignmentMarkdown, pointsMarkdown));
    assertTrue(created.compare(assignmentMarkdown, headerMarkdown));
    assertTrue(created.compare(pointsMarkdown, headerMarkdown));

    assertTrue(created.compare(assignmentMarkdown, assignmentMarkdown));
    assertTrue(created.compare(pointsMarkdown, pointsMarkdown));
    assertTrue(created.compare(headerMarkdown, headerMarkdown));

    assertFalse(created.compare(headerMarkdown, assignmentMarkdown));
    assertFalse(created.compare(headerMarkdown, pointsMarkdown));
  }
}

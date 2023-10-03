package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MarkdownFileWalkerVisitor
 */
public class MarkdownFileWalkerVisitorTest {

  // The path to the folder we want to analyze, read, walk, etc.
  Path sampleInputsDir = Path.of("src/test/resources/SampleInputs/");
  MarkdownFileWalkerVisitor simpleWalker = new MarkdownFileWalkerVisitor();

  Path testDir = Path.of("src/test/");
  MarkdownFileWalkerVisitor complexWalker = new MarkdownFileWalkerVisitor();

  File assignmentNotes =
      new File("src/test/resources/SampleInputs/ProgrammingAssignmentNotes.md");
  BasicFileAttributes assignmentAttributes;

  /**
   * Initializes the walkers before each tests and ensures an exception is not thrown.
   */
  @BeforeEach
  public void initData() {
    try {
      Files.walkFileTree(sampleInputsDir, simpleWalker);
      Files.walkFileTree(testDir, complexWalker);
    } catch (IOException e) {
      fail();
    }

  }

  /**
   * Tests the fileWalker as it traverses through a directory and should identify
   * Markdown files to analyze.
   *
   * @throws IOException - An error thrown during traversal of file system
   */
  @Test
  public void testFileWalker() throws IOException {
    // should only be 9 successful creation of markdown files
    assertEquals(8, simpleWalker.identified.size());
    assertEquals(9, complexWalker.identified.size());
    assertThrows(
        IOException.class,
        () -> complexWalker.visitFileFailed(assignmentNotes.toPath(), new IOException()));
  }
}

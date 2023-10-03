package cs3500.pa01;

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
 * Tests the FileWriter class and its methods
 */
public class FileWriterTest {

  FileWriter writer = new FileWriter();

  /**
   * Tests writing to a certain location - both overwriting a certain file
   * and creating a new file if it does not already exist
   */
  @Test
  public void testWriteToLoc() {
    // writing to a file that doesn't exist
    File target = new File("src/test/resources/SampleInputs/Summarized");
    writer.writeToFile(target, "# I hope this works");
    try {
      Scanner sc = new Scanner(new File(target + ".md"));
      sc.useDelimiter("\n");
      assertEquals("# I hope this works", sc.next());
    } catch (IOException e) {
      System.err.println(e);
      fail();
    }

    // writing to a file that does exist, want to overwrite
    File overTarget = new File("src/test/resources/SampleInputs/ToOverwrite");
    writer.writeToFile(overTarget, "# Shall these tests pass?\n"
        + "- We will find out and see");
    try {
      Scanner sc = new Scanner(new File(overTarget + ".md"));
      sc.useDelimiter("\n");
      assertEquals("# Shall these tests pass?", sc.next());
      assertEquals("- We will find out and see", sc.next());
    } catch (IOException e) {
      System.err.println(e);
      fail();
    }

  }

  /**
   * Ensures that exceptions are thrown when instructed to create files at locations that
   * do not exist
   */
  @Test
  public void testWriteInvalidLoc() {
    assertThrows(
        RuntimeException.class,
        () -> writer.writeToFile(new File("/akdshf/adjsfhadsfa"), "this won't work"));
  }

}

package cs3500.pa01;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for writing new / overwriting files, particularly for displaying a "summarized" markdown
 * file.
 */
public class FileWriter {

  /**
   * Writes the given String (Markdown Contents) to the given filepath (appending a .md).
   *
   * @param file     where to write the contents, without an ending extension... a .md is appended
   * @param contents contents to write to the file
   */
  public void writeToFile(File file, String contents) {
    File modifiedFile = new File(file + ".md");

    byte[] data = contents.getBytes();

    try {
      modifiedFile.createNewFile();
      FileOutputStream stream = new FileOutputStream(modifiedFile, false);
      stream.write(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}

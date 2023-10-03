package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Provides instructions for when the file system is traversed.
 */
public class MarkdownFileWalkerVisitor implements FileVisitor<Path> {
  ArrayList<Markdown> identified = new ArrayList<Markdown>();

  /**
   * Handles the opening of a new directory.
   *
   * @param dir   - a reference to the directory
   * @param attrs - the directory's basic attributes
   * @return - enumerated instructions for the traversal of the file system
   * @throws IOException - an issue that may have occured while traversing file system
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    System.out.println("Visiting directory " + dir);
    return CONTINUE;
  }

  /**
   * Handles the visiting of a file.
   *
   * @param file  - a reference to the file
   * @param attrs - the file's basic attributes
   * @return - enumerated instructions for the traversal of the file system
   * @throws IOException - an issue that may have occured while traversing file system
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    System.out.println("Visiting file " + file);
    if (attrs.isRegularFile() && file.toString().endsWith(".md")) {
      System.out.println("Trying to make markdown...");
      try {
        identified.add(new Markdown(file.toFile(), attrs));
      } catch (IllegalArgumentException e) {
        System.err.println(e);
      }
    }
    return CONTINUE;
  }

  /**
   * Handles the failure/rejection of attempting to visit a file
   *
   * @param file - a reference to the file
   * @param exc  - the I/O exception that prevented the file from being visited
   * @return - enumerated instructions for the traversal of the file system
   * @throws IOException - an issue that may have occured while traversing file system
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    throw new IOException("Could not visit file " + file + " due to " + exc);
  }

  /**
   * Handles the completion of visiting a certain directory.
   *
   * @param dir - a reference to the directory
   * @param exc - {@code null} if the iteration of the directory completes without
   *            an error; otherwise the I/O exception that caused the iteration
   *            of the directory to complete prematurely
   * @return - enumerated instructions for the traversal of the file system
   * @throws IOException - an issue that may have occured while traversing file system
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    System.out.println("Finished directory " + dir);
    return CONTINUE;
  }

}



package cs3500.pa01;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

/**
 * Represents a full Markdown file as a list of items (in the order they appear in the document).
 */
public class Markdown {
  public ArrayList<IMarkdownItem> items;
  private File reference;
  private FileTime creationTime;
  private FileTime modifiedTime;

  /**
   * Takes in a Markdown (or txt file for testing) file and both the time it was created + last
   * modified to create a Markdown object.
   *
   * @param toAnalyze - Markdown file to analyze and create objects from
   * @param attrs     - Object of attributes of file passed into constructor
   */
  public Markdown(File toAnalyze, BasicFileAttributes attrs) {
    this.reference = toAnalyze;
    Analyzer looker = new Analyzer();
    this.items = looker.analyze(toAnalyze);
    this.creationTime = attrs.creationTime();
    this.modifiedTime = attrs.lastModifiedTime();
  }

  /**
   * Constructor primarily used for testing, reads from given FileTimes rather than the
   * given BasicFileAttributes associated with given file.
   *
   * @param toAnalyze    - Markdown file to analyze and create objects from
   * @param creationTime - time the Markdown file was created
   * @param modifiedTime - time the Markdown file was last modified
   */
  public Markdown(File toAnalyze, FileTime creationTime, FileTime modifiedTime) {
    this.reference = toAnalyze;
    Analyzer looker = new Analyzer();
    this.items = looker.analyze(toAnalyze);
    this.creationTime = creationTime;
    this.modifiedTime = modifiedTime;
  }

  /**
   * Returns the name this Markdown object's file is associated with.
   *
   * @return - the name of this Markdown object.
   */
  public String getName() {
    return this.reference.getName();
  }

  /**
   * Returns the time this Markdown object's file was created.
   *
   * @return - the time of creation of this markdown object's file
   */
  public FileTime getCreationTime() {
    return this.creationTime;
  }

  /**
   * Returns the time this Markdown object's file was last modified.
   *
   * @return - the last time of modification of this markdown object's file
   */
  public FileTime getModifiedTime() {
    return modifiedTime;
  }

  /**
   * Summarizes this Markdown file, including only headers and any important points
   * that may be located below each header. Other points will be removed.
   */
  public void summarize() {
    StringBuilder important = new StringBuilder();

    for (IMarkdownItem item : this.items) {
      important.append(item.getImportant());
    }

    Analyzer analyzer = new Analyzer();
    this.items = analyzer.analyze(important.toString());
  }

  /**
   * Outputs this Markdown file as a String, either in Markdown form
   * or not.
   *
   * @param inMarkdown - whether this file should be output in Markdown form
   * @return - this Markdown file as a String
   */
  public String toString(boolean inMarkdown) {
    StringBuilder result = new StringBuilder();

    for (IMarkdownItem item : items) {
      result.append(item.toString(inMarkdown));
    }

    return result.toString();
  }

  /**
   * Returns a list of MarkdownQuestions that are contained in the file that this Markdown
   * object references.
   *
   * @return - the MarkdownQuestions contained in the file this Markdown references.
   */
  public ArrayList<MarkdownQuestion> getQuestions() {
    Analyzer reader = new Analyzer();
    return reader.analyzeQuestions(this.reference);
  }
}

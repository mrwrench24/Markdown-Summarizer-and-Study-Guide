package cs3500.pa01;

/**
 * Represents an object by which Markdown files are compared by the last
 * time they were modified.
 */
public class ModifiedComparison implements IComparison {
  /**
   * Compares two Markdown files by the last time they were modified.
   *
   * @param file1 - first markdown file, testing whether this
   *              should come before/equal to second file
   * @param file2 - second markdown file, testing whether
   *              another file should come before/equal to this
   * @return - Whether first file comes before / is equivalent to second file
   */
  public boolean compare(Markdown file1, Markdown file2) {
    return (file1.getModifiedTime().compareTo(file2.getModifiedTime()) <= 0);
  }
}

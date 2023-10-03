package cs3500.pa01;

/**
 * Compares two markdown files by the time they were created.
 */
public class CreationComparison implements IComparison {
  /**
   * Compares two markdown files by the time they were created. Returns true if first file should
   * come before/is equal to second file
   *
   * @param file1 - first markdown file, testing whether this should come
   *             before/equal to second file
   * @param file2 - second markdown file, testing whether another file
   *              should come before/equal to this
   * @return - Whether first file was created before/same time as second file
   */
  public boolean compare(Markdown file1, Markdown file2) {
    return (file1.getCreationTime().compareTo(file2.getCreationTime()) <= 0);
  }
}

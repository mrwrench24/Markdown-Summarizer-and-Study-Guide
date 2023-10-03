package cs3500.pa01;

/**
 * Represents an object by which files are compared by their name.
 */
public class NameComparison implements IComparison {
  /**
   * Compares the two files by name. Returns true if first Markdown file should come
   * before or is equivalent to second file, false if otherwise.
   *
   * @param file1 - first markdown file, testing whether this should come
   *              before/equal to second file
   * @param file2 - second markdown file, testing whether another file should
   *              come before/equal to this
   * @return - Whether first file should come before/equal to second file.
   */
  public boolean compare(Markdown file1, Markdown file2) {
    return (file1.getName().compareTo(file2.getName()) <= 0);
  }
}

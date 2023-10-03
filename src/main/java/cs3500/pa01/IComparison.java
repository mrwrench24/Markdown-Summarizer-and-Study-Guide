package cs3500.pa01;

/**
 * Represents a method by which two Markdown files can be compared.
 */
public interface IComparison {
  /**
   * Compares two Markdown files, returns true if file1 should come before / is equal to
   * file two
   *
   * @param file1 - first markdown file, testing whether this should come
   *              before/equal to second file
   * @param file2 - second markdown file, testing whether another file should come
   *              before/equal to this
   * @return - whether the first file should come before/equal to second file
   */
  boolean compare(Markdown file1, Markdown file2);
}

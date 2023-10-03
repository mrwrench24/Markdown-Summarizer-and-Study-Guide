package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a MarkdownItem, which is either a header (MarkdownHeader)
 * or a bullet point (MarkdownPoint).
 */
public interface IMarkdownItem {
  /**
   * Returns this MarkdownItem as a String in Markdown form or not.
   *
   * @param inMarkdown - whether this MarkdownItem should be return in its
   *                   markdown form or not.
   * @return - this MarkdownItem as a String
   */
  public String toString(boolean inMarkdown);

  /**
   * Extracts, prints this MarkdownItem's important items, meaning items
   * which contain another item or a MarkdownPhrase(s) itself that is important.
   *
   * @return - a list of items that eventually lead to an important MarkdownPhrase.
   */
  public String getImportant();
}

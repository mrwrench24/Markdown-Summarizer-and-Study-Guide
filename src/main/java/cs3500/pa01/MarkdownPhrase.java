package cs3500.pa01;

/**
 * Represents part of a "bullet point" in Markdown. Each phrase
 * contains a certain bit of text and is either considered "important"
 * or not.
 */
public class MarkdownPhrase {
  String content;
  boolean important;

  /**
   * Default constructor for MarkdownPhrase, takes in its content and, based on supplied
   * string, whether it is important or not.
   *
   * @param str - the content this MarkdownPhrase holds
   */
  MarkdownPhrase(String str) {
    this.content = str;
    // only checking for first brackets because others are guaranteed per assignment
    // guidelines... and the supplied string is already ending with them per
    // file reader
    this.important = content.startsWith("[[");
  }

  /**
   * Represents this phrase as a String, either in Markdown form or not, based
   * on supplied boolean argument.
   *
   * @param inMarkdown - whether we want this phrase's string representation to be
   *                   in markdown form (if it is important, include the brackets)
   * @return This phrase as a String, either in Markdown form or not.
   */
  String toString(boolean inMarkdown) {
    if (!inMarkdown && this.important) {
      // we don't want markdown and it has brackets - remove first + last 2 chars
      return this.content.substring(2, this.content.length() - 2);
    } else {
      return this.content;
    }
  }
}

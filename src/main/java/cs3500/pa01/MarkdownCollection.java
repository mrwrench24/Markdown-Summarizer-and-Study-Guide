package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a SORTED collection of Markdown files, sorted by the supplied
 * IComparison object's compare method. Markdown files are added one by one
 * using the addMarkdown method to maintain sorted status.
 */
public class MarkdownCollection {
  public ArrayList<Markdown> collection = new ArrayList<Markdown>();
  private IComparison sortingMethod;

  MarkdownCollection(IComparison method) {
    this.sortingMethod = method;
  }

  /**
   * Adds given Markdown file into appropriate place based on IComparison
   * method associated with this MarkdownCollection.
   *
   * @param toAdd - the Markdown file we want to add into this collection into appropriate
   *              position.
   */
  public void addMarkdown(Markdown toAdd) {

    for (int i = 0; i < collection.size(); i++) {
      // if toAdd should come before current file,
      if (sortingMethod.compare(toAdd, collection.get(i))) {
        collection.add(i, toAdd);
        return;
      }
    }
    collection.add(toAdd);
  }

  /**
   * Modifies this collection such that every file in it is summarized and only contains
   * important information from now on.
   */
  public void summarize() {
    for (Markdown file : collection) {
      file.summarize();
    }
  }

  /**
   * Outputs this collection (in order) as a String.
   *
   * @return - the files in this collection as a String.
   */
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < collection.size(); i++) {
      Markdown file = collection.get(i);
      String toAdd = file.toString(true);

      if (i == 0) {
        toAdd = toAdd.replaceFirst("\n", "");
      }

      result.append(toAdd);
    }

    return result.toString();
  }

  /**
   * Returns a list of all Markdown Questions contained in the Markdown files in this
   * collection.
   *
   * @return - A list of all the MarkdownQuestions associated with this collection.
   */
  public ArrayList<MarkdownQuestion> getQuestions() {
    ArrayList<MarkdownQuestion> result = new ArrayList<>();
    for (Markdown mdFile : this.collection) {
      for (MarkdownQuestion question : mdFile.getQuestions()) {
        result.add(question);
      }
    }

    return result;
  }

}

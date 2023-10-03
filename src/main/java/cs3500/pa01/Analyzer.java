package cs3500.pa01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents an object for reading input either from Files or Strings in Markdown
 * form and creating a list of appropriate IMarkdownItems that can be worked with
 * and represent the input.
 */
public class Analyzer {

  /**
   * Reads input from a file line by line and creates IMarkdownItems as appropriate
   * based on input read. Assumes the file is properly formatted, meaning lines are
   * either a header (and start with #) or a bullet point (start with -). Returns a
   * list of items in the order they were found in the file.
   *
   * @param file - The File to be analyzed, must be in Markdown form
   * @return - A list of IMarkdownItems as they were found/placed in the file.
   */
  public ArrayList<IMarkdownItem> analyze(File file) {
    ArrayList<IMarkdownItem> itemsFound = new ArrayList<IMarkdownItem>();
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(file));
      sc.useDelimiter("\n");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    return this.readInput(sc);
  }

  /**
   * Reads input from a String line by line and creates IMarkdownItems as appropriate
   * based on input read. Assumes the String is properly formatted, meaning lines are
   * either a header (and start with #) or a bullet point (start with -). Returns a
   * list of items in the order they were found in the file.
   *
   * @param message - The String to be analyzed, in Markdown form
   * @return - A list of IMarkdownItems as they were found/placed in the file.
   */
  public ArrayList<IMarkdownItem> analyze(String message) {
    ArrayList<IMarkdownItem> itemsFound = new ArrayList<IMarkdownItem>();
    Scanner sc = new Scanner(message);
    sc.useDelimiter("\n");

    return this.readInput(sc);
  }

  /**
   * Reads input line by line (based on \n escape sequence) from given Scanner
   * and creates IMarkdownItems as appropriate from the current line.
   *
   * @param sc - A scanner currently reading input from a Markdown-formatted file
   *           or String.
   * @return - A list of workable IMarkdownItem(s) detected from the scanner.
   */
  private ArrayList<IMarkdownItem> readInput(Scanner sc) {
    ArrayList<IMarkdownItem> itemsFound = new ArrayList<IMarkdownItem>();

    while (sc.hasNext()) {
      String currentLine = sc.next();

      if (currentLine.startsWith("#")) {
        itemsFound.add(new MarkdownHeader(currentLine));
      } else if (currentLine.startsWith("-")) {
        this.pointHelper(currentLine, sc, itemsFound);
      }
    }

    return itemsFound;
  }

  /**
   * Extracts list of questions from given file (markdown), ignoring non-question items
   *
   * @param toRead - the file from which we want to read and extract Markdown Questions from
   * @return - a list of properly formatted, workable MarkdownQuestions as contained in the file
   */
  public ArrayList<MarkdownQuestion> analyzeQuestions(File toRead) {
    Scanner sc;

    try {
      sc = new Scanner(toRead);
      sc.useDelimiter("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return this.readQuestionsInput(sc);
  }

  /**
   * Reads input from the scanner line by line and extracts/ builds questions in an ArrayList
   * as appropriate.
   *
   * @param sc - the scanner we want to read input, build questions from
   * @return - a list of appropriately built MarkdownQuestions read from scanner.
   */
  private ArrayList<MarkdownQuestion> readQuestionsInput(Scanner sc) {
    ArrayList<MarkdownQuestion> itemsFound = new ArrayList<>();

    while (sc.hasNext()) {
      String currentLine = sc.next();
      int beginningIndex = currentLine.indexOf("[[");

      if (beginningIndex != -1) {
        currentLine = this.searchFor("]]", currentLine, sc);
        int questionIndex = currentLine.indexOf(":::");
        int endingIndex = currentLine.indexOf("]]");
        if (questionIndex > beginningIndex && endingIndex > questionIndex) {
          currentLine = this.preFormat(currentLine);
          itemsFound.add(new MarkdownQuestion(currentLine));
        }
      }
    }

    return itemsFound;
  }

  /**
   * Uses scanner to search for given sequence, appending content scanned to the supplied
   * "alreadyHave" string. If the given string already contains the given sequence, that String
   * on its own will be returned.
   *
   * @param sequence    - the sequence we want our final result to contain.
   * @param alreadyHave - the String we have already scanned.
   * @param toUse       - the scanner to use and extract strings/content from
   * @return - The String containing the sequence, or an exception if the sequence isn't found.
   */
  private String searchFor(String sequence, String alreadyHave, Scanner toUse) {
    toUse.useDelimiter("");

    while (!alreadyHave.contains(sequence) && toUse.hasNext()) {
      if (toUse.hasNext()) {
        alreadyHave += toUse.next();
      } else {
        throw new RuntimeException(
            "No more lines to read, did not find needed " + sequence);
      }
    }

    toUse.useDelimiter("\n");

    return alreadyHave;
  }

  /**
   * Contains some of the logic associated with creating MarkdownPoints. Continues searching
   * for the "]]" ending as appropriate, and as long as the final result is not a question block,
   * it will be added to the list of items.
   *
   * @param currentLine - the line we have so far which *is* a MarkdownPoint, but may not have
   *                    been fully scanned/read yet.
   * @param sc          - the scanner being used to read the file and extract items
   * @param itemList    - the list of items to add to, if appropriate
   */
  private void pointHelper(String currentLine, Scanner sc, ArrayList<IMarkdownItem> itemList) {
    if (currentLine.contains("[[")) {
      currentLine = this.searchFor("]]", currentLine, sc);
    }

    if (!currentLine.contains(":::")) {
      currentLine = currentLine.substring(1);
      currentLine = currentLine.trim();
      currentLine = currentLine.replace("\n", " ");
      itemList.add(new MarkdownPoint(currentLine));
    }

  }

  /**
   * Formats a String in Markdown form that is going to become a Markdown Question (passes into
   * the constructor as a string). Removes anything before the question block and replaces
   * line breaks to keep it one line.
   *
   * @param toFormat - the String we want to format for entry into the Question constructor
   * @return - the properly formatted String.
   */
  private String preFormat(String toFormat) {
    int beginningIndex = toFormat.indexOf("[[");
    toFormat = toFormat.substring(beginningIndex);
    toFormat = toFormat.replace("\n", "");
    return toFormat;
  }
}

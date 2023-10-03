package cs3500.pa01;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a .sr file that contains only Markdown questions and answers, in the form:
 * [[QUESTION:::ANSWER]]]DIFFICULTYABBREVIATION
 */
public class QuestionFile {
  File reference;

  /**
   * Initializes a new QuestionFile. Takes in a .sr file that this object will reference.
   *
   * @param toReference - a .sr file that this object will reference.
   */
  QuestionFile(File toReference) {
    if (toReference.toString().endsWith(".sr")) {
      this.reference = toReference;
    } else {
      throw new IllegalArgumentException("Given file " + toReference + " is not .sr file");
    }
  }

  /**
   * Reads input line-by-line from this object's .sr file and returns a list of extracted
   * MarkdownQuestions contained within the file.
   *
   * @return - a list of MarkdownQuestions contained within this object's .sr file
   */
  public ArrayList<MarkdownQuestion> readFile() {
    ArrayList<MarkdownQuestion> result = new ArrayList<>();
    Scanner sc;

    try {
      sc = new Scanner(this.reference);
      sc.useDelimiter("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    while (sc.hasNext()) {
      String currentLine = sc.next();
      if (!currentLine.equals("")) {
        MarkdownQuestion toAdd = this.extractQuestion(currentLine);
        result.add(toAdd);
      }
    }

    return result;
  }

  /**
   * Takes in a line (String) from a .sr file (properly formatted as a question) and returns a
   * MarkdownQuestion representing the given line.
   *
   * @param currentLine - a line from a .sr file, containing a properly formatted question in
   *                    Markdown form.
   * @return - A MarkdownQuestion representing the given line from the .sr file.
   */
  private MarkdownQuestion extractQuestion(String currentLine) {
    int separatorIndex = currentLine.indexOf(":::");
    String questionText = currentLine.substring(2, separatorIndex);
    String answerText = currentLine.substring(separatorIndex + 3, currentLine.length() - 3);

    String difficultyMarker = currentLine.substring(currentLine.length() - 1);

    return new MarkdownQuestion(questionText, answerText, difficultyMarker);
  }

  /**
   * Overwrites the content's of this QuestionFile's reference and replaces it with
   * the list of questions written (likely as an update to the file after running a
   * Study Session).
   *
   * @param updatedList - the list of questions we want to write to the file.
   */
  public void writeToFile(ArrayList<MarkdownQuestion> updatedList) {
    StringBuilder questionsAsString = new StringBuilder();

    for (MarkdownQuestion question : updatedList) {
      questionsAsString.append(question.toString(true) + "\n");
    }

    byte[] data = questionsAsString.toString().getBytes();

    try {
      FileOutputStream stream = new FileOutputStream(this.reference, false);
      stream.write(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

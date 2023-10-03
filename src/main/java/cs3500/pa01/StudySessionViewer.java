package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents the "view" for a study session. Has a number of methods that print
 * out certain text to the user to guide them through their study session.
 */
public class StudySessionViewer {
  /**
   * Welcomes the user to the StudySession and indicates that they need to input
   * the number of questions they would want to complete.
   */
  public void welcome() {
    String message = "Welcome to StudySession - 3500 Edition!";
    message += "\nUp to how many questions would you like to study today?";
    System.out.println(message);
  }

  /**
   * Displays the given question to the user and displays a list of next options
   * for the user to take.
   *
   * @param question - the question to display to the user.
   */
  public void display(MarkdownQuestion question) {
    StringBuilder message = new StringBuilder();
    message.append("Question: " + question.question);
    message.append("\n" + question.originalDifficulty.toString() + " question");
    message.append("\n\n1. Mark as Easy");
    message.append("\n2. Mark as Hard");
    message.append("\n3. Reveal Answer");

    System.out.println(message.toString());
  }

  /**
   * Asks the user to input a number, does not print a new line after message.
   */
  public void promptInput() {
    System.out.print("Please enter a number: ");
  }

  /**
   * Summarizes the StudySession for the user.
   */
  public void summarize(ArrayList<Statistic> stats) {
    System.out.println("Great job! Here's some information about your Study Session:\n\n");

    for (Statistic stat : stats) {
      System.out.println(stat.toString());
    }

    System.out.println("Thank you for participating in this enrichment activity."
        + "\nRemember, the cake is a LIE. Goodbye!");
  }

  /**
   * Scolds the user for sending in invalid input, tells them what values their input
   * may take.
   *
   * @param minInput - the mininmum input the user may put in.
   * @param maxInput - the maximum input the user may put in.
   */
  public void scold(int minInput, int maxInput) {
    System.out.println("Please input between " + minInput + " and " + maxInput + ", inclusive");
  }

  /**
   * Reveals given String to the user, noting in the message that it is the "answer" to a question.
   *
   * @param answer - the answer to be displayed to the user.
   */
  public void showAnswer(String answer) {
    System.out.println("The answer is: " + answer);
  }
}

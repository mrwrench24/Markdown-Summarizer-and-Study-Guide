package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents the Controller for a StudySession, calls functionalit(ies) of the model
 * and has the viewer display certain pre-set messages to the user when appropriate.
 */
public class StudySessionController {
  private StudySessionModel model;
  private StudySessionViewer viewer;
  private UserInputHandler handler;
  private UserAction[] actionList = UserAction.values();

  /**
   * Default constructor, takes in a model and builds a new inputHandler / viewer to use.
   *
   * @param toUse - the StudySessionModel we want to use.
   */
  StudySessionController(StudySessionModel toUse) {
    this.model = toUse;
    this.viewer = new StudySessionViewer();
    this.handler = new UserInputHandler();
  }

  /**
   * Testing constructor, takes in both a model and a userInputHandler (so one can be used
   * that reads directly from a file)
   *
   * @param toUse       - the StudySessionModel this controller will use
   * @param testHandler - the UIH this controller will request inputs from.
   */
  StudySessionController(StudySessionModel toUse, UserInputHandler testHandler) {
    this.model = toUse;
    this.viewer = new StudySessionViewer();
    this.handler = testHandler;
  }

  /**
   * Runs a full StudySession, including initial setup, displaying of questions + handling input,
   * and displaying a summary of statistics after the session is complete.
   */
  public void runSession() {
    this.runSetup();

    while (model.hasQuestions()) {
      viewer.display(model.getQuestion());
      try {
        int userInput = getInput(1, actionList.length);
        this.handleInput(userInput);
      } catch (IllegalArgumentException e) {
        viewer.scold(1, actionList.length);
      }
    }

    ArrayList<Statistic> stats = model.getStats();
    viewer.summarize(stats);

    model.endSession();
  }

  /**
   * Handles the initial setup of a StudySession, including welcoming the user,
   * prompting a number of questions to ask, and setting up the model with that
   * number of questions.
   */
  public void runSetup() {
    viewer.welcome();
    int numQuestions = getInput(1, Integer.MAX_VALUE);
    model.setup(numQuestions);
  }

  /**
   * Handles the user input, the input has already been checked to be between 1 and the list
   * of the action list.
   *
   * @param input - a number between 1 and the length of the action list corresponding to the
   *              user's desired action.
   */
  private void handleInput(int input) {
    UserAction[] actionList = UserAction.values();
    UserAction actionTaken = actionList[input - 1];

    if (actionTaken.equals(UserAction.MARKEASY)) {
      model.markQuestion(Difficulty.EASY);
    } else if (actionTaken.equals(UserAction.MARKHARD)) {
      model.markQuestion(Difficulty.HARD);
    } else if (actionTaken.equals(UserAction.REVEALANSWER)) {
      // NEED TO DISPLAY THE ANSWER TO THE USER!!!!!
      viewer.showAnswer(model.getAnswer());
      model.increment();
    }
  }

  /**
   * Will repeatedly prompt user for input until a valid number between given minimum and
   * maximum (both inclusive) is received.
   *
   * @param minInput - the smallest value the user input may take.
   * @param maxInput - the largest value the user input may take.
   * @return - user input between min and max value (inclusive).
   */
  private int getInput(int minInput, int maxInput) {
    while (true) {

      viewer.promptInput();

      try {
        int userInput = handler.nextInput(minInput, maxInput);
        return userInput;
      } catch (IllegalArgumentException e) {
        viewer.scold(minInput, maxInput);
      }
    }

  }
}

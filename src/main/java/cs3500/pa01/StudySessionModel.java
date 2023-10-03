package cs3500.pa01;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the model of a Study Session. Tracks important information regarding
 * a study session.
 */
public class StudySessionModel {
  private QuestionFile reference;
  private ArrayList<MarkdownQuestion> questionsToUse = new ArrayList<MarkdownQuestion>();
  private ArrayList<MarkdownQuestion> questionsNotUsed = new ArrayList<MarkdownQuestion>();
  private int questionIndex = 0;
  private int startingEasy;
  private int startingHard;

  /**
   * Builds a new study session model reliant upon the given questionfile, both reading and writing
   * to the file as appropriate while running the session.
   *
   * @param toUse - the question file that this model is build on
   */
  StudySessionModel(QuestionFile toUse) {
    this.reference = toUse;
  }

  /**
   * Generates a list of up to provided number of questions, and denotes certain statistics
   * for after the session is complete.
   *
   * @param numQuestions - the maximum number of questions the user would like to study.
   */
  public void setup(int numQuestions) {
    ArrayList<MarkdownQuestion> allQuestions = reference.readFile();
    Collections.shuffle(allQuestions);

    this.extractOnly(questionsToUse, allQuestions, Difficulty.HARD, numQuestions);
    this.extractOnly(questionsToUse, allQuestions, Difficulty.EASY, numQuestions);

    questionsNotUsed = allQuestions;

    startingEasy = countOfDifficulty(questionsToUse, Difficulty.EASY);
    startingHard = countOfDifficulty(questionsToUse, Difficulty.HARD);
  }

  /**
   * Handles the end of a study session, including rewriting/overwriting file with all
   * the new metadata about questions
   */
  public void endSession() {
    questionsToUse.addAll(questionsNotUsed);

    for (MarkdownQuestion question : questionsToUse) {
      question.originalDifficulty = question.newDifficulty;
    }

    reference.writeToFile(questionsToUse);
  }

  /**
   * Scans the given "source" list for questions with an original difficulty of supplied
   * difficulty enumeration. Questions will only be added to the result list as long as it
   * remains below the given size. When questions are added to the result list, they are
   * removed from the source list.
   *
   * @param result            - A list of questions we want to add to.
   * @param source            - A list of questions that we want to take some from.
   * @param desiredDifficulty - The difficulty we want the questions we add to have.
   * @param maxSize           - The maximum size our result list can be. Final result may be less.
   */
  private void extractOnly(ArrayList<MarkdownQuestion> result, ArrayList<MarkdownQuestion> source,
                           Difficulty desiredDifficulty, int maxSize) {
    int sourceIndex = 0;

    while (sourceIndex < source.size() && result.size() < maxSize) {
      MarkdownQuestion currentQuestion = source.get(sourceIndex);

      if (currentQuestion.originalDifficulty.equals(desiredDifficulty)) {
        result.add(currentQuestion);
        source.remove(sourceIndex);
        sourceIndex--;
      }

      sourceIndex++;
    }
  }

  /**
   * Returns whether this Model has a "next" question to display.
   *
   * @return - whether this Model has a "next" question to display.
   */
  public boolean hasQuestions() {
    return questionIndex < questionsToUse.size();
  }

  /**
   * Returns the "next" or "current" question for this model.
   *
   * @return - The question this study session is currently using.
   */
  public MarkdownQuestion getQuestion() {
    return questionsToUse.get(questionIndex);
  }

  /**
   * Marks the currentQuestion as being of given difficulty.
   *
   * @param toMark - the Difficulty we want to mark the current question as being.
   */
  public void markQuestion(Difficulty toMark) {
    questionsToUse.get(questionIndex).newDifficulty = toMark;
  }

  /**
   * Returns the answer of the current question in this model.
   *
   * @return - the answer to the question.
   */
  public String getAnswer() {
    return questionsToUse.get(questionIndex).answer;
  }

  /**
   * Moves this studySession onto the next index in the question list. Should call hasQuestions
   * to ensure there are still questions remaining to be displayed/studied.
   */
  public void increment() {
    questionIndex++;
  }

  /**
   * Returns a list of statistics pertaining to this StudySession. Builds (and returns) statistics
   * about the number of questions answered, the number of questions which went from easy to hard,
   * the number of questions which went from hard to easy, the total number of questions that are
   * now easy (in session and in bank), and the total number of questions that are now hard.
   *
   * @return - Statistics pertaining to this StudySession.
   */
  public ArrayList<Statistic> getStats() {
    ArrayList<Statistic> result = new ArrayList<>();

    Statistic questionsAnswered = new Statistic("Questions in Session", questionsToUse.size());
    result.add(questionsAnswered);

    int questionsEasyToHard = countOfDifficulty(questionsToUse, Difficulty.EASY, Difficulty.HARD);
    Statistic easyToHard = new Statistic("Questions from Easy to Hard", questionsEasyToHard);
    result.add(easyToHard);

    int questionsHardToEasy = countOfDifficulty(questionsToUse, Difficulty.HARD, Difficulty.EASY);
    Statistic hardToEasy = new Statistic("Questions from Hard to Easy", questionsHardToEasy);
    result.add(hardToEasy);

    int totalQuestionsEasy =
        countOfDifficulty(questionsToUse, Difficulty.EASY)
            + countOfDifficulty(questionsNotUsed, Difficulty.EASY);
    Statistic totalEasy = new Statistic("Easy Questions now in Bank", totalQuestionsEasy);
    result.add(totalEasy);

    int totalQuestionsHard =
        countOfDifficulty(questionsToUse, Difficulty.HARD)
            + countOfDifficulty(questionsNotUsed, Difficulty.HARD);
    Statistic totalHard = new Statistic("Hard Questions now in Bank", totalQuestionsHard);
    result.add(totalHard);

    return result;
  }

  /**
   * Returns the number of questions in given list with a "new difficulty" of given
   * difficulty.
   *
   * @param questions  - the list of questions we want to count from.
   * @param difficulty - the difficulty we want to count the number of questions as having
   * @return - the number of questions in the list with the given difficulty.
   */
  private int countOfDifficulty(ArrayList<MarkdownQuestion> questions, Difficulty difficulty) {
    int count = 0;

    for (MarkdownQuestion question : questions) {
      if (question.newDifficulty.equals(difficulty)) {
        count++;
      }
    }

    return count;
  }

  /**
   * Returns the number of questions in given list with matching given original difficulty and
   * new difficulty. (Used to track questions which went from easy to hard, or hard to easy,
   * for example).
   *
   * @param questions - the list of questions we want to count from.
   * @param original  - the "originalDifficulty" we want to count the number of questions as having
   * @param newDiff   - the "newDifficulty" we want to count the number of questions as having
   * @return - the number of questions in the list matching both original and new difficulty given.
   */
  private int countOfDifficulty(ArrayList<MarkdownQuestion> questions, Difficulty original,
                                Difficulty newDiff) {
    int count = 0;

    for (MarkdownQuestion question : questions) {
      if (question.originalDifficulty.equals(original)
          && question.newDifficulty.equals(newDiff)) {
        count++;
      }
    }

    return count;
  }
}

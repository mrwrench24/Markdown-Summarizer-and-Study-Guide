package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Tests the Analyzer class and its methods
 */
public class AnalyzerTest {

  static final String SAMPLE_DIRECTORY = "src/test/resources/SampleInputs/";

  // Last modified May 14 at 7:38 AM
  File assignmentNotes = new File(SAMPLE_DIRECTORY + "ProgrammingAssignmentNotes.md");

  // Last modified May 14 at 8:18 AM
  File pointsOnly = new File(SAMPLE_DIRECTORY + "PointsOnly.md");

  // Last modified May 14 at 8:46 AM
  File headersOnly = new File(SAMPLE_DIRECTORY + "HeadersOnly.md");

  File notAFile = new File(SAMPLE_DIRECTORY + "jkdlksapfiojqer.txt");

  File questionsToReadFile = new File(SAMPLE_DIRECTORY + "QuestionsToRead.md");

  File megaMarkdown = new File(SAMPLE_DIRECTORY + "MegaMarkdown.md");

  String biologyString =
      "# Intro to Biology\n"
          + "- [[Biology is the foundation of life.]]\n"
          + "- Everything is made up of biology.\n"
          + "- A lot goes into biology, but [[cells are the building block to all life.]]\n"
          + "- Studying biology is tough, but [[jobs in medicine are stable]] and rewarding.\n\n";
  // I added an extra \n there so I can test that the analyzer just skips over empty lines.

  /**
   * Tests ONLY the Analyzer's ability to read, detect, and create accurate MarkdownPoints
   * from a file being read
   */
  @Test
  public void testMarkdownPoints() {
    Analyzer analyzer = new Analyzer();
    ArrayList<IMarkdownItem> items = analyzer.analyze(pointsOnly);

    assertEquals(4, items.size());
    assertEquals("- This is just bullet points\n", items.get(0).toString(true));
    assertEquals("- Don't expect anything else\n", items.get(1).toString(true));
    assertEquals("- [[Some of them might be important]] but other's won't\n",
        items.get(2).toString(true));
    assertEquals("- Some of them might be important but other's won't\n",
        items.get(2).toString(false));
    assertEquals("- Be careful because [[testing your code is so important]]\n",
        items.get(3).toString(true));
  }

  /**
   * Tests ONLY the analyzer's ability to read, detect, create accurate MarkdownPoints
   * from a file.
   */
  @Test
  public void testBasicHeaders() {
    Analyzer analyzer = new Analyzer();
    ArrayList<IMarkdownItem> items = analyzer.analyze(headersOnly);

    assertEquals(3, items.size());
    assertEquals("\n## This file only contains headers.\n",
        items.get(0).toString(true));
    assertEquals("\n## To keep it simple, these headers are all "
        + "going to be of the same importance.\n", items.get(1).toString(true));
    assertEquals("\n## Things will get a lot messier "
            + "later though, when we try and test nesting headers and items.\n",
        items.get(2).toString(true));
  }

  /**
   * Tests the analyzer's ability to do a combination of IMarkdownItem creation when
   * reading from a full Markdown file.
   */
  @Test
  public void testFullFileAnalysis() {
    Analyzer analyzer = new Analyzer();
    ArrayList<IMarkdownItem> items = analyzer.analyze(assignmentNotes);

    assertEquals(21, items.size());
    assertEquals("\n# Welcome to PA01\n", items.get(0).toString(true));
    assertEquals("- This is the first programming assignment\n",
        items.get(1).toString(true));
    assertEquals("- [[You need to summarize markdown files]]\n",
        items.get(2).toString(true));
    assertEquals("- You need to summarize markdown files\n",
        items.get(2).toString(false));

    assertThrows(
        RuntimeException.class,
        () -> analyzer.analyze(notAFile));
  }

  /**
   * Tests the analyzer's ability to do a combination of IMarkdownItem creation when
   * reading from a String that represents/resembles a Markdown file (namely created
   * when a Markdown object is summarized)
   */
  @Test
  public void testFullStringAnalysis() {
    Analyzer analyzer = new Analyzer();
    ArrayList<IMarkdownItem> items = analyzer.analyze(biologyString);

    assertEquals(5, items.size());
    assertEquals("\n# Intro to Biology\n", items.get(0).toString(true));
    assertEquals("\n Intro to Biology\n", items.get(0).toString(false));

    assertEquals("- [[Biology is the foundation of life.]]\n",
        items.get(1).toString(true));
    assertEquals("- Biology is the foundation of life.\n",
        items.get(1).toString(false));
  }

  /**
   * Tests Analyzer's analyzeQuestions method
   */
  @Test
  public void testAnalyzeQuestions() {
    Analyzer analyzer = new Analyzer();
    ArrayList<MarkdownQuestion> noQuestions = analyzer.analyzeQuestions(pointsOnly);
    assertEquals(0, noQuestions.size());

    ArrayList<MarkdownQuestion> fileQuestions = analyzer.analyzeQuestions(questionsToReadFile);
    assertEquals(3, fileQuestions.size());
    assertEquals("[[1 + 1:::2]]H", fileQuestions.get(0).toString(true));
    assertEquals("[[There can be up to __ levels of headers:::4]]H",
        fileQuestions.get(1).toString(true));
    assertEquals("[[Your questions can:::also be multi-line]]H",
        fileQuestions.get(2).toString(true));
  }

  /**
   * Tests Analyzer's analyzeQuestions and analyze methods on a "mega" file that contains
   * questions, points, headers, etc.
   */
  @Test
  public void testAnalyzeMegaMarkdown() {
    Analyzer analyzer = new Analyzer();
    ArrayList<IMarkdownItem> itemAnalyze = analyzer.analyze(megaMarkdown);
    assertEquals(14, itemAnalyze.size());

    assertEquals("\n# The ultimate test begins now\n", itemAnalyze.get(0).toString(true));
    assertEquals("- We are going to test all kinds of crazy things\n",
        itemAnalyze.get(1).toString(true));
    assertEquals("- And put the \"Analyzer\" class to the ultimate test\n",
        itemAnalyze.get(2).toString(true));

    assertEquals(
        "- [[There is nothing more satisfying than seeing those green JaCoCo bars sometimes...]]\n",
        itemAnalyze.get(5).toString(true));

    ArrayList<MarkdownQuestion> questionAnalyze = analyzer.analyzeQuestions(megaMarkdown);
    assertEquals(5, questionAnalyze.size());
  }

  /**
   * Ensures that analyzer throws an exception when instructed to analyzeQuestions from
   * an invalid directory
   */
  @Test
  public void testAnalyzeQuestionsFailure() {
    Analyzer analyzer = new Analyzer();
    assertThrows(
        RuntimeException.class,
        () -> analyzer.analyzeQuestions(new File("src/test/SampleInputs/NotAFile.sr"))
    );
  }
}


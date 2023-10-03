package cs3500.pa01;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point, begins the summarizing process based on arguments supplied
   *
   * @param args - Should contain either three arguments or no arguments.
   *             If three arguments: The first argument should be a path, either
   *             relative or absolute, to a folder which we want to summarize the Markdown files
   *             that it contains. The second should be "filename", "created", or "modified",
   *             indicating the methodology by which we want our final summarized notes to be
   *             organized by. The last argument should be a path - including file name - to
   *             where the summarized points will be written.
   *
   *             If no arguments: A study session is run, the user is asked for a path to
   *             run a StudySession on.
   */
  public static void main(String[] args) {
    if (args.length == 3) {
      try {
        summarizing(args);
      } catch (IOException e) {
        throw new RuntimeException("Files could not be summarized due to error: " + e);
      }
    } else if (args.length == 0) {
      try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Sure, let's do a StudySession!\n"
            + "Please enter a path to the .sr file you want to use: ");
        String userInput = sc.nextLine();
        studySession(new File(userInput));
      } catch (IOException e) {
        throw new RuntimeException(
            "Could not run study session on summarized questions due to error: " + e);
      }
    } else {
      throw new IllegalArgumentException("Invalid number of arguments supplied");
    }
  }

  /**
   * Handles various actions associated with setting up and displaying the summarizing process
   * for a markdown file.
   *
   * @param args - three command line arguments, the first being a path to a folder/directory
   *             we want to analyze, the second being a certain phrase representing a methodology
   *             by which files should be sorted, and the third being a path to where the
   *             summarized file is written.
   * @throws IOException - if files could not be analyzed properly or a given path led to file
   *                     that doesn't exist.
   */
  private static void summarizing(String[] args) throws IOException {
    MarkdownCollection collectionToUse;

    try {
      collectionToUse = buildCollectionFrom(args);
    } catch (IOException e) {
      throw new RuntimeException("Could not walk file tree due to exception: " + e);
    }

    // Lastly: Writes the collection (summarized) to the desired location
    File toWrite = new File(args[2]);
    FileWriter writer = new FileWriter();

    collectionToUse.summarize();
    writer.writeToFile(toWrite, collectionToUse.toString());
  }

  /**
   * Responsible for returning associated IComparison with given String which should correspond
   * to a certain flagName.
   *
   * @param flagName - a String that should be either "FILENAME", "CREATED", or "MODIFIED"
   * @return - the IComparison method associated with given String
   */
  private static IComparison identifyFlag(String flagName) {
    flagName = flagName.toUpperCase();

    if (flagName.equals(FlagName.FILENAME.toString())) {
      return new NameComparison();
    } else if (flagName.equals(FlagName.CREATED.toString())) {
      return new CreationComparison();
    } else if (flagName.equals(FlagName.MODIFIED.toString())) {
      return new ModifiedComparison();
    } else {
      throw new IllegalArgumentException("Invalid flagname/sorting methodology");
    }
  }

  private static void studySession(File toStudy) throws IOException {
    QuestionFile studyqf;
    try {
      studyqf = new QuestionFile(toStudy);
    } catch (IllegalArgumentException e) {
      throw new IOException("Invalid file supplied to run a study session on");
    }

    StudySessionModel model = new StudySessionModel(studyqf);
    StudySessionController controller = new StudySessionController(model);
    controller.runSession();
  }

  private static MarkdownCollection buildCollectionFrom(String[] args) throws IOException {
    // First: Reads and creates Markdown objects for every .md file found in directory
    Path root = Path.of(args[0]);
    MarkdownFileWalkerVisitor fileVisitor = new MarkdownFileWalkerVisitor();
    Files.walkFileTree(root, fileVisitor);

    // Second: Finds appropriate IComparison and creates MarkdownCollection with that object
    IComparison toUse = identifyFlag(args[1]);
    MarkdownCollection collection = new MarkdownCollection(toUse);

    for (Markdown file : fileVisitor.identified) {
      collection.addMarkdown(file);
    }

    return collection;
  }
}
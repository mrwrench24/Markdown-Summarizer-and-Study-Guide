package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests MarkdownHeader construction and methods
 */
public class HeaderTest {

  MarkdownHeader test4 = new MarkdownHeader("#### low");
  MarkdownHeader test3 = new MarkdownHeader("### mid");
  MarkdownHeader test2 = new MarkdownHeader("## ok");
  MarkdownHeader test1 = new MarkdownHeader("# high");


  /*
  # Intro to Biology
  - [[Biology is the foundation of life.]]
  - Everything is made up of biology.
  - A lot goes into biology, but [[cells are the building block to all life.]]
  - Studying biology is tough, but [[jobs in medicine are stable]] and rewarding.
   */

  /*
  MarkdownPoint biology1 = new MarkdownPoint("[[Biology is the foundation of life.]]");
  MarkdownPoint biology2 = new MarkdownPoint("Everything is made up of biology.");
  MarkdownPoint biology3 = new MarkdownPoint("A lot goes into biology, "
      + "but [[cells are the building block to all life.]]");
  MarkdownPoint biology4 = new MarkdownPoint("Studying biology is tough, "
      + "but [[jobs in medicine are stable]] and rewarding.");

  ArrayList<IMarkdownItem> biologyList = new ArrayList<IMarkdownItem>
      (Arrays.asList(biology1, biology2, biology3, biology4));

  MarkdownHeader biologyHeader = new MarkdownHeader("# Intro to Biology", biologyList);

  String biologyInMarkdown = new String("# Intro to Biology\n"
      + "- [[Biology is the foundation of life.]]\n"
      + "- Everything is made up of biology.\n"
      + "- A lot goes into biology, but [[cells are the building block to all life.]]\n"
      + "- Studying biology is tough, but [[jobs in medicine are stable]] and rewarding.\n");

  String biologyInPlain = new String(" Intro to Biology\n"
      + "- Biology is the foundation of life.\n"
      + "- Everything is made up of biology.\n"
      + "- A lot goes into biology, but cells are the building block to all life.\n"
      + "- Studying biology is tough, but jobs in medicine are stable and rewarding.\n");

   */

  /* COMPUTER SCIENCE:
  # Computer Science
  - Relatively new field in academia
  - [[Requires about four years of study]]
  - You won't write that many english papers but [[you will write a lot of code!]]
  ## Java
  - [[Java is an object-oriented, statically typed language]]
  - [[There are eight primitive types in Java]], many of which you won't use
  - This project is currently being written in Java!
  ### Java Libraries
  - Honestly [[I still don't know what libraries are]] but I'm sure I'll figure it out
  - Frankly [[I only made this tab for testing]] because [[this little note sheet is massive!]]
  ## Python
  - Python looks like a ton of gibberish
  - [[People who code in Python are probably impatient]] because it is so abbreviated
  - Python is [[very good for use in Data Science]] and maybe [[Artificial Intelligence, too]]
   */

  /*
  MarkdownPoint python1 = new MarkdownPoint("Python looks like a ton of gibberish");
  MarkdownPoint python2 =
      new MarkdownPoint("[[People who code in Python are probably impatient]]"
          + " because it is so abbreviated");
  MarkdownPoint python3 =
      new MarkdownPoint("Python is [[very good for use in Data Science]]"
          + " and maybe [[Artificial Intelligence, too]]");

  ArrayList<IMarkdownItem> pythonList = new
      ArrayList<IMarkdownItem>(Arrays.asList(python1, python2, python3));

  MarkdownHeader pythonHeader = new MarkdownHeader("## Python", pythonList);

  MarkdownPoint libaries1 = new MarkdownPoint("Honestly "
      + "[[I still don't know what libraries are]] but I'm sure I'll figure it out");
  MarkdownPoint libraries2 = new MarkdownPoint("Frankly [[I only made this tab for "
      + "testing]] because [[this little note sheet is massive!]]");

  ArrayList<IMarkdownItem> librariesList =
      new ArrayList<IMarkdownItem>(Arrays.asList(libaries1, libraries2));

  MarkdownHeader librariesHeader = new MarkdownHeader("### Java Libraries", librariesList);

  MarkdownPoint java1 = new MarkdownPoint("[[Java is an object-oriented, "
      + "statically typed language]]");
  MarkdownPoint java2 = new MarkdownPoint("[[There are eight primitive types in Java]],"
      + " many of which you won't use");
  MarkdownPoint java3 = new MarkdownPoint("This project is currently "
      + "being written in Java!");

  ArrayList<IMarkdownItem> javaList =
      new ArrayList<IMarkdownItem>(Arrays.asList(java1, java2, java3, librariesHeader));

  MarkdownHeader javaHeader = new MarkdownHeader("## Java", javaList);

  MarkdownPoint compSci1 = new MarkdownPoint("Relatively new field in academia");
  MarkdownPoint compSci2 = new MarkdownPoint("[[Requires about four years of study]]");
  MarkdownPoint compSci3 = new MarkdownPoint("You won't write that many english papers but"
      + " [[you will write a lot of code!]]");

  ArrayList<IMarkdownItem> compSciList = new ArrayList<IMarkdownItem>
      (Arrays.asList(compSci1, compSci2, compSci3, javaHeader, pythonHeader));

  MarkdownHeader compSciHeader =
      new MarkdownHeader("# Computer Science", compSciList);

   */


  /**
   * Tests construction of MarkdownHeader - namely their ability to extract accurate
   * "importance" levels and associated names
   */
  @Test
  public void testHeaderConstructor() {
    assertEquals(1, test1.headingLevel);
    assertEquals(2, test2.headingLevel);
    assertEquals(3, test3.headingLevel);
    assertEquals(4, test4.headingLevel);

    assertEquals(" high", test1.name);
    assertEquals(" ok", test2.name);
    assertEquals(" mid", test3.name);
    assertEquals(" low", test4.name);

    // non-header given to header constructor (not enough # signs, btw)
    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownHeader("hello")
    );

    // only ## signs given to header constructor - must have content!
    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownHeader("##")
    );

    // Too many ## signs given to header constructor
    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownHeader("##### this constructor will fail")
    );
  }

  /**
   * Tests MarkdownHeader's toString method
   */
  @Test
  public void testToString() {
    assertEquals("\n#### low\n", test4.toString(true));
    assertEquals("\n### mid\n", test3.toString(true));
    assertEquals("\n## ok\n", test2.toString(true));
    assertEquals("\n# high\n", test1.toString(true));

    assertEquals("\n low\n", test4.toString(false));
    assertEquals("\n mid\n", test3.toString(false));
    assertEquals("\n ok\n", test2.toString(false));
    assertEquals("\n high\n", test1.toString(false));
  }


  /**
   * Tests the header's get important method
   */
  @Test
  public void testGetImportant() {
    assertEquals("\n#### low\n", test4.getImportant());
    assertEquals("\n# high\n", test1.getImportant());
  }
}

package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests constructions and methods of MarkdownPoints.
 */
public class PointTest {
  // hello [[world]]!
  MarkdownPhrase hello = new MarkdownPhrase("hello ");
  MarkdownPhrase world = new MarkdownPhrase("[[world]]");
  MarkdownPhrase exclaim = new MarkdownPhrase("!");

  ArrayList<MarkdownPhrase> helloMarkdown =
      new ArrayList<MarkdownPhrase>(Arrays.asList(hello, world, exclaim));

  MarkdownPhrase thisIs = new MarkdownPhrase("this is so ");
  MarkdownPhrase important = new MarkdownPhrase("[[important]]");

  ArrayList<MarkdownPhrase> importantMarkdown =
      new ArrayList<MarkdownPhrase>(Arrays.asList(thisIs, important));

  // build one using a list of phrases, another just using a markdown string
  MarkdownPoint listPoint = new MarkdownPoint(helloMarkdown);
  MarkdownPoint stringPoint = new MarkdownPoint("hello [[world]]!");

  MarkdownPoint importantPoint = new MarkdownPoint(importantMarkdown);
  MarkdownPoint strImportantPoint = new MarkdownPoint("this is so [[important]]");

  MarkdownPoint firstSecond = new MarkdownPoint("[[first]]second");

  MarkdownPoint notImportant = new MarkdownPoint("This phrase really isn't that important");

  MarkdownPoint wakeUp = new MarkdownPoint("[[Wake up!]]");

  MarkdownPoint between = new MarkdownPoint("[[Between]] two [[worlds]]");

  MarkdownPoint complexPhrase =
      new MarkdownPoint("[[It is probably]] a good idea when [[you]] "
          + "test your [[code with]] complex [[phrases!]]");

  /**
   * Tests the construction of Markdown points, namely the constructors' abilities to denote
   * and build important/non important phrases accurately and separately
   */
  @Test
  public void testPointConstruction() {
    // the way the method works, there is an empty "phrase" built
    assertEquals("[[first]]", firstSecond.phrase.get(1).content);
    assertEquals("second", firstSecond.phrase.get(2).content);

    assertTrue(firstSecond.phrase.get(1).important);
    assertFalse(firstSecond.phrase.get(2).important);

    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownPoint("this is [[ so broken"));

    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownPoint("well, try ]] this too!"));

    assertThrows(
        IllegalArgumentException.class,
        () -> new MarkdownPoint("]] a man, a plan, [[ panama"));
  }

  /**
   * Tests the toString method for MarkdownPoints
   */
  @Test
  public void testToString() {
    assertEquals("- hello [[world]]!\n", listPoint.toString(true));
    assertEquals("- hello [[world]]!\n", stringPoint.toString(true));

    assertEquals("- hello world!\n", listPoint.toString(false));
    assertEquals("- hello world!\n", stringPoint.toString(false));

    assertEquals("- this is so [[important]]\n", importantPoint.toString(true));
    assertEquals("- this is so [[important]]\n", strImportantPoint.toString(true));

    assertEquals("- this is so important\n", importantPoint.toString(false));
    assertEquals("- this is so important\n", strImportantPoint.toString(false));

    assertEquals("- [[first]]second\n", firstSecond.toString(true));
    assertEquals("- firstsecond\n", firstSecond.toString(false));

    assertEquals("- This phrase really isn't that important\n",
        notImportant.toString(true));
    assertEquals("- This phrase really isn't that important\n",
        notImportant.toString(false));

    assertEquals("- [[Wake up!]]\n", wakeUp.toString(true));
    assertEquals("- Wake up!\n", wakeUp.toString(false));

    assertEquals("- [[Between]] two [[worlds]]\n", between.toString(true));
    assertEquals("- Between two worlds\n", between.toString(false));

    assertEquals("- [[It is probably]] a good idea when [[you]] "
        + "test your [[code with]] complex [[phrases!]]\n", complexPhrase.toString(true));
    assertEquals("- It is probably a good idea when you "
        + "test your code with complex phrases!\n", complexPhrase.toString(false));
  }

  /**
   * Tests the getImportant method for MarkdownPoints
   */
  @Test
  public void testGetImportant() {
    assertEquals("- important\n", strImportantPoint.getImportant());
    assertEquals("- important\n", importantPoint.getImportant());

    assertEquals("- Between\n- worlds\n", between.getImportant());
    assertEquals("- It is probably\n- you\n- code with\n- phrases!\n",
        complexPhrase.getImportant());
  }
}

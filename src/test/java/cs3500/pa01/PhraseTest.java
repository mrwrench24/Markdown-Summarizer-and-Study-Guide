package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the MarkdownPhrase class and its associated methods.
 */

public class PhraseTest {

  MarkdownPhrase helloWorld = new MarkdownPhrase("Hello world!");
  MarkdownPhrase helloWorldImp = new MarkdownPhrase("[[Hello world!]]");


  /**
   * Makes sure that MarkdownPhrase's constructor accurately marks creations
   * as important or not important based on supplied strings
   */
  @Test
  public void testPhraseConstructor() {
    assertFalse(helloWorld.important);
    assertTrue(helloWorldImp.important);
  }

  /**
   * Tests the MarkdownPhrase's toString method, ensuring it is/isn't written
   * in Markdown form when appropriate
   */
  @Test
  public void testPhraseToString() {
    assertEquals("Hello world!", helloWorld.toString(true));
    assertEquals("Hello world!", helloWorld.toString(false));
    assertEquals("[[Hello world!]]", helloWorldImp.toString(true));
    assertEquals("Hello world!", helloWorldImp.toString(false));
  }

}

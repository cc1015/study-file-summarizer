package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for AbContent abstract class.
 */
class AbContentTest {
  Header h1 = new Header("header", 2);
  Header h2 = new Header("header", 2);
  ImportantInfo i1 = new ImportantInfo("info");

  /**
   * Check that getContent returns correct string of content
   */
  @Test
  public void testGetContent() {
    assertEquals(h1.getContent(), "header");
    assertEquals(i1.getContent(), "info");
  }

  /**
   * Check that sameAbContent returns true for two different headers that have the same content
   * and number of #
   */
  @Test
  public void testSameAbContentPass() {
    assertTrue(h1.sameAbContent(h2));
  }

  /**
   * Check that samAbContent returns false for a header and important info
   */
  @Test
  public void testSameAbContentFail() {
    assertFalse(i1.sameAbContent(h1));
  }
}
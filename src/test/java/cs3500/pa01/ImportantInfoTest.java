package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for the ImportantInfo class.
 */
class ImportantInfoTest {
  ImportantInfo blankInfo = new ImportantInfo("");
  ImportantInfo impInfo = new ImportantInfo("example info");
  ImportantInfo impInfo2 = new ImportantInfo("example info");

  /**
   * Checks if ImportantInfo is initialized and constructed properly.
   */
  @Test
  public void testConstructInfo() {
    ImportantInfo i = new ImportantInfo("i");
    assertNotNull(i);
  }

  /**
   * Checks if writeContent() returns "- " on ImportantInfo that has "" as content.
   */
  @Test
  public void testToStringBlank() {
    assertEquals(blankInfo.writeContent(), "- ");
  }

  /**
   * Checks if writeContent() returns "- example info" on ImportantInfo that has "example info"
   * as content.
   */
  @Test
  public void testToStringInfo() {
    assertEquals(impInfo.writeContent(), "- example info");
  }

  /**
   * Checks that sameAbContent returns true for two ImportantInfos with the same String content
   */
  @Test
  public void testSameAbContentTrue() {
    assertTrue(impInfo.sameAbContent(impInfo2));
  }

  /**
   * Checks that sameAbContent returns false for two ImportantInfos with different String content
   */
  @Test
  public void testSamAbContentFalse() {
    assertFalse(impInfo.sameAbContent(blankInfo));
  }
}
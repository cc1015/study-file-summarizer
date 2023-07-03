package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for FileSummarizer class.
 */
class FileSummarizerTest {
  Header mainHeader;
  Header subHeader;
  ArrayList<AbContent> summary;
  StudyGuide sd;

  /**
   * Initialize data; creates a study guide with a main header (# header)
   */
  @BeforeEach
  public void initData() {
    this.mainHeader = new Header("header", 1);
    this.subHeader = new Header("subheader", 2);

    this.summary = new ArrayList<>();
    summary.add(mainHeader);

    this.sd = new StudyGuide(summary);
  }

  /**
   * Checks that the constructor initializes properly
   */
  @Test
  public void testConstruct() {
    FileSummarizer fs = new FileSummarizer("src/test/resources/test_files/only_header.md");
    assertNotNull(fs);
  }

  /**
   * Checks that a non-existent file cannot be used to construct a FileSummarizer
   */
  @Test
  public void testNoAccessFile() {
    assertThrows(IllegalArgumentException.class,
        () -> new FileSummarizer("src/test/resources/test_files/no_file.md"));
  }

  /**
   * Checks that a header is parsed correctly
   */
  @Test
  public void testHeaderParse() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/only_header.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that important info in one line is parsed correctly
   */
  @Test
  public void testInfoParse() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/only_info.md");

    ImportantInfo i1 = new ImportantInfo("important information");
    summary.remove(mainHeader);
    summary.add(i1);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that important information spanning two lines gets parsed correctly and saved as one
   * piece of important information
   * Input:
   * # header
   * - this is an example and here is [[important information
   * that spans two lines.]]
   * Expected output:
   * # header
   * - important information that spans two lines
   */
  @Test
  public void testTwoLineImpInfo() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/two_line_imp_info.md");

    ImportantInfo i1 = new ImportantInfo("important information that spans two lines.");
    summary.add(i1);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line that has two pieces of important information gets parsed correctly and
   * saved as two separate pieces of important information
   * Input:
   * # header
   * - this is [[important info 1]] and [[important info 2]]
   * ## subheader
   * - this is [[important info 3]] and [[important info 4]]
   * Expected output:
   * # header
   * - important info 1
   * - important info 2
   * ## subheader
   * - important info
   * - important info
   */
  @Test
  public void testTwoImpInfoInLine() {
    ImportantInfo i1 = new ImportantInfo("important info 1");
    ImportantInfo i2 = new ImportantInfo("important info 2");
    ImportantInfo i3 = new ImportantInfo("important info 3");
    ImportantInfo i4 = new ImportantInfo("important info 4");
    summary.add(i1);
    summary.add(i2);
    summary.add(subHeader);
    summary.add(i3);
    summary.add(i4);

    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/two_imp_in_line.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line that has a # (not important) gets parsed correctly and not mistaken
   * for a header
   * Input:
   * # header
   * - this is info # 1: a line with a pound sign
   * ## subheader
   * - this is [[important info]]
   * Expected output:
   * # header
   * ## header
   * - important info
   */
  @Test
  public void testPoundInBullet() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/pound_in_bullet.md");

    ImportantInfo i = new ImportantInfo("important info");
    summary.add(subHeader);
    summary.add(i);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line that has a # at the start of a line (not important) gets parsed correctly
   * and not mistaken for a header
   * Input:
   * # header
   * - this is [[important info]] and also has a pound sign:
   * #.
   * ## subheader
   * Expected output:
   * # header
   * - important info
   * ## header
   */
  @Test
  public void testPoundBeginLine() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/pound_begin_line.md");

    ImportantInfo i = new ImportantInfo("important info");
    summary.add(i);
    summary.add(subHeader);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line that has a # in between [[ ]] gets parsed correctly and saved as
   * important information
   * Input:
   * # header
   * - this is [[important info with a pound sign: #]]
   * - this is more [[# important info]]
   * ## subheader
   * - this is [[subheader important info with a pound sign: #]]
   * Expected output:
   * # header
   * - important info with a pound sign: #
   * - # important info
   * ## header
   * - subheader important info with a pound sign: #
   */
  @Test
  public void testPoundImpInfo() {
    ImportantInfo i1 = new ImportantInfo("important info with a pound sign: #");
    ImportantInfo i2 = new ImportantInfo("# important info");
    ImportantInfo i3 = new ImportantInfo("subheader important info with a pound sign: #");
    summary.add(i1);
    summary.add(i2);
    summary.add(subHeader);
    summary.add(i3);

    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/pound_in_imp_info.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a header with no bullet and another header underneath it gets parsed correctly
   * as two separate headers
   * Input:
   * # header
   * ## subheader
   * Expected output:
   * # header
   * ## subheader
   */
  @Test
  public void testHeaderNoBullet() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/header_no_bullet.md");

    summary.add(subHeader);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a bullet with important information with no header above and another bullet
   * underneath with no important information get parsed correctly
   * Input:
   * - this is [[a bullet with no header with important info]]
   * - this is a bullet with no header and no important info
   * Expected output:
   * - a bullet with no header with important info
   */
  @Test
  public void testBulletWithNoHeader() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/bullet_no_header.md");

    ImportantInfo i1 = new ImportantInfo("a bullet with no header with important info");
    summary.remove(mainHeader);
    summary.add(i1);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that an indented bullet with important information gets parsed correctly and saved as
   * important information
   * Input:
   * # header
   * - this is [[highlight]]
   * - this is [[indented important information]]
   * ## subheader
   * - more [[indented important information under subheader]]
   * - [[even more indented important information!]]
   * Expected output:
   * # header
   * - highlight
   * - indented important information
   * ## subheader
   * - indented important information under subheader
   * - even more indented important information!
   */
  @Test
  public void testIndentedBullet() {
    ImportantInfo i1 = new ImportantInfo("highlight");
    ImportantInfo i2 = new ImportantInfo("indented important information");
    ImportantInfo i3 = new ImportantInfo("indented important information under subheader");
    ImportantInfo i4 = new ImportantInfo("even more indented important information!");
    summary.add(i1);
    summary.add(i2);
    summary.add(subHeader);
    summary.add(i3);
    summary.add(i4);

    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/indented_bullet.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a single bracket gets parsed correctly and not mistaken as important information
   * Input:
   * # header
   * - this is a line with a single bracket [ ]
   * - this is [[important info]]
   * Expected output:
   * # header
   * - important info
   */
  @Test
  public void testSingleBracket() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/single_bracket.md");

    ImportantInfo i = new ImportantInfo("important info");
    summary.add(i);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a single bracket in important information gets parsed correctly and
   * not mistaken as more important information
   * Input:
   * # header
   * - this is [[a line with [single brackets] inside]]
   * Expected output:
   * # header
   * - a line with [single brackets] inside
   */
  @Test
  public void testSingBracketInImpInfo() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/single_bracket_imp_info.md");

    ImportantInfo i = new ImportantInfo("a line with [single brackets] inside");
    summary.add(i);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line with a [[ but no ]] gets parsed correctly and not mistaken as important
   * information
   * Input:
   * # header
   * - this is [[a line with no ending double bracket
   * Expected output:
   * # header
   */
  @Test
  public void testNoEndingDoubleBracket() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_ending_double_bracket.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line with a ]] but no [[ gets parsed correctly and not mistaken as important
   * information
   * Input:
   * # header
   * - this is a line with only end ]] double brackets
   * Expected output:
   * # header
   */
  @Test
  public void testNoStartDoubleBracket() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_start_double_bracket.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a header with a # gets parsed correctly and not mistaken for another header
   * Input:
   * # header
   * ## subheader with # pound sign
   * Expected output:
   * # header
   * ## subheader with # pound sign
   */
  @Test
  public void testPoundInHeader() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/pound_in_header.md");

    Header h = new Header("subheader with # pound sign", 2);
    summary.add(h);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a header that is just #s and no space after gets parsed correctly and saved
   * as a header
   * Input:
   * # header
   * ####
   * Expected output:
   * # header
   * ####
   */
  @Test
  public void testNoTextHeaderNoSpace() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_text_header.md");

    Header h = new Header("", 4);
    summary.add(h);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a header that is just #s and a space after gets parsed correctly and saved
   * as a header
   * Input:
   * # header
   * #####
   * Expected output:
   * # header
   * #####
   */
  @Test
  public void testNoTextHeaderSpace() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_text_header_space.md");

    Header h = new Header("", 5);
    summary.add(h);

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line with too many #s with text does not get parsed as a header
   * Input:
   * # header
   * ######## invalid header
   * Expected output:
   * # header
   */
  @Test
  public void testTooManyPound() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/too_many_pound.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line with too many #s and no text does not get parsed as a header
   * Input:
   * # header
   * ########
   * Expected output:
   * # header
   */
  @Test
  public void testTooManyPoundNoText() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_text_header_2.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /**
   * Checks that a line with #s and text not seperated by a space does not get parsed as a header
   * Input:
   * # header
   * ##bad header
   * Expected output:
   * # header
   */
  @Test
  public void testNoSpaceAfterPoundWithText() {
    FileSummarizer fs =
        new FileSummarizer("src/test/resources/test_files/no_space_pound_w_text.md");

    StudyGuide test = new StudyGuide(fs.createSummary());

    assertEquals(test.writeStudyGuide(), sd.writeStudyGuide());
  }

  /*
  edge cases tested:
  - two line important info
  - two important info in one line
  - # in bullet
  - # at start of line
  - # in important info
  - header with no bullet under
  - bullet with no header
  - indented bullet
  - single bracket
  - single bracket inside imp info
  - no ending double bracket
  - only ending double bracket
  - # in header
  - # only header no space after
  - # only header space after
  - too many #
  - no space after # with text after
 */
}
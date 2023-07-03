package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A test class for the StudyGuide class
 */
class StudyGuideTest {
  Header mainHeader;
  ImportantInfo cellDef;
  Header nucleus;
  ImportantInfo nucleusDef;
  Header er;
  Header roughEr;
  ImportantInfo roughErdef;
  ImportantInfo roughErInfo;
  Header smoothEr;
  ImportantInfo smoothErdef;
  ArrayList<AbContent> notes;
  StudyGuide bio;

  ImportantInfo info;
  StudyGuide onlyInfo;

  /**
   * Initialize data.
   */
  @BeforeEach
  public void initData() {
    this.mainHeader = new Header("The Cell", 1);
    this.cellDef = new ImportantInfo("smallest unit of an organism");
    this.nucleus = new Header("Nucleus", 2);
    this.nucleusDef = new ImportantInfo("contains DNA");
    this.er = new Header("ER", 2);
    this.roughEr = new Header("Rough ER", 3);
    this.roughErdef = new ImportantInfo("synthesizes proteins");
    this.roughErInfo = new ImportantInfo("holds ribosomes");
    this.smoothEr = new Header("Smooth ER", 3);
    this.smoothErdef = new ImportantInfo("synthesizes lipids");

    this.notes = new ArrayList<>();
    notes.add(mainHeader);
    notes.add(cellDef);
    notes.add(nucleus);
    notes.add(nucleusDef);
    notes.add(er);
    notes.add(roughEr);
    notes.add(roughErdef);
    notes.add(roughErInfo);
    notes.add(smoothEr);
    notes.add(smoothErdef);

    this.bio = new StudyGuide(notes);

    this.info = new ImportantInfo("info");
    ArrayList<AbContent> n = new ArrayList<>();
    n.add(info);
    this.onlyInfo = new StudyGuide(n);
  }

  /**
   * Checks that constructor initializes properly
   */
  @Test
  public void testConstruct() {
    assertNotNull(this.onlyInfo);
  }

  /**
   * Checks that writeStudyGuide produces the correct list of Strings
   */
  @Test
  public void testWriteStudyGuide() {
    ArrayList<String> strings = new ArrayList<>();
    strings.add("\n# The Cell");
    strings.add("- smallest unit of an organism");
    strings.add("\n## Nucleus");
    strings.add("- contains DNA");
    strings.add("\n## ER");
    strings.add("\n### Rough ER");
    strings.add("- synthesizes proteins");
    strings.add("- holds ribosomes");
    strings.add("\n### Smooth ER");
    strings.add("- synthesizes lipids");

    assertEquals(this.bio.writeStudyGuide(), strings);
  }
}
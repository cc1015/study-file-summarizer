package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a study guide that has a list of AbContent representing the notes in the study
 * guide.
 */
public class StudyGuide {
  private ArrayList<AbContent> notes;

  /**
   * Constructor sets this StudyGuide's notes to the given list of AbContent.
   *
   * @param notes the list of AbContent to be set to this StudyGuide's notes.
   */
  StudyGuide(ArrayList<AbContent> notes) {
    this.notes = notes;
  }

  /**
   * Writes each AbContent as a String in this StudyGuides notes by calling writeContent for
   * each AbContent.
   *
   * @return a list of Strings representing the list of AbContent as Strings
   */
  public ArrayList<String> writeStudyGuide() {
    ArrayList<String> strings = new ArrayList<>();

    // for every AbContent in this StudyGuide's list of notes, call writeContent method to convert
    // to String
    for (AbContent a : this.notes) {
      strings.add(a.writeContent());
    }
    return strings;
  }
}

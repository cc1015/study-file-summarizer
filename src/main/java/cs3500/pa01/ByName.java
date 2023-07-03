package cs3500.pa01;

import java.util.Comparator;

/**
 * Represents a Comparator that compares two given MdFiles alphabetically by name.
 */
public class ByName implements Comparator<MdFile> {

  /**
   * Takes in two MdFiles and compares by file name: if f1 is alphabetically before f2, returns
   * number greater than 0, if f2 is alphabetically before f1, returns number less than 0, if
   * f1 and f2 have the same name, returns 0.
   *
   * @param f1 the first MdFile to be compared.
   * @param f2 the second MdFile to be compared.
   *
   * @return integer less than 0 if f1 is alphabetically before f2, greater than 0 if f1 is
   *         alphabetically after f2, and 0 if f1 and f2 hae the same name.
   */
  @Override
  public int compare(MdFile f1, MdFile f2) {
    String f1Name = f1.getName();
    String f2Name = f2.getName();

    return f1Name.compareTo(f2Name);
  }
}

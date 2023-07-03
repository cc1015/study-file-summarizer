package cs3500.pa01;

import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Represents a Comparator that compares two given MdFiles by last modified time.
 */
public class ByModified implements Comparator<MdFile> {

  /**
   * Takes in two MdFiles and compares by modified time: if f1 was modified before f2, returns
   * less than 0, if f2 was modified before f1, returns number greater than 0, if f1 and f2
   * have the same modified time, returns 0.
   *
   * @param f1 the first MdFile to be compared.
   * @param f2 the second MdFile to be compared.
   *
   * @return integer less than 0 if f1 was modified before f2, greater than 0 if f1 was modified
   *         after f2, and 0 if f1 and f2 were modified at the same time.
   */
  @Override
  public int compare(MdFile f1, MdFile f2) {
    FileTime f1ModTime = f1.getModifyTime();
    FileTime f2ModTime = f2.getModifyTime();

    return f1ModTime.compareTo(f2ModTime);
  }
}

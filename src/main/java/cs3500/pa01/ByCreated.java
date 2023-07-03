package cs3500.pa01;

import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Represents a Comparator that compares two given MdFiles by creation time.
 */
public class ByCreated implements Comparator<MdFile> {

  /**
   * Takes in two MdFiles and compares by time created: if f1 was created before f2, returns
   * number greater than 0, if f2 was created before f1, returns number less than 0, if f1 and f2
   * have the same creation time, returns 0.
   *
   * @param f1 the first MdFile to be compared.
   * @param f2 the second MdFile to be compared.
   *
   * @return integer less than 0 if f1 was created before f2, greater than 0 if f1 was created
   *         after f2, and 0 if f1 and f2 were created at the same time.
   */
  @Override
  public int compare(MdFile f1, MdFile f2) {
    FileTime f1Time = f1.getCreationTime();
    FileTime f2Time = f2.getCreationTime();

    return f1Time.compareTo(f2Time);
  }
}

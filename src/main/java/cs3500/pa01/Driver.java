package cs3500.pa01;

/**
 * The main method of this project. Houses the main method entry point.
 */
public class Driver {
  /**
   * Program entry point; delegates to the FileCreator class to create the study guide file.
   *
   * @param  args root directory, sorting mechanism, destination directory
   */
  public static void main(String[] args) {
    String root = args[0];
    String sortingMech = args[1];
    String destination = args[2];

    FileCreator f = new FileCreator(root, sortingMech, destination);
    f.createStudyGuide();
  }
}

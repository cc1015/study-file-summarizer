package cs3500.pa01;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a class that creates a study guide file.
 */
public class FileCreator {
  private Path rootPath;
  private Comparator<MdFile> comp;
  private String destPath;

  /**
   * Constructs with given rootPath, comp, and destPath. Throws IllegalArgumentException if comp is
   * not filename, modified, or created, or if the destPath is not an .md file.
   *
   * @param rootPath the root directory of the .md files to be collected and summarized
   * @param comp     the comparator to sort the files by
   * @param destPath the destination path of the study guide file
   */
  FileCreator(String rootPath, String comp, String destPath) {
    this.rootPath = Path.of(rootPath);

    if (comp.equalsIgnoreCase("filename")) {
      this.comp = new ByName();
    } else if (comp.equalsIgnoreCase("modified")) {
      this.comp = new ByModified();
    } else if ((comp.equalsIgnoreCase("created"))) {
      this.comp = new ByCreated();
    } else {
      throw new IllegalArgumentException("Sorting mechanism must either be 'filename', "
          + "'modified', or 'created'");
    }

    if (destPath.endsWith(".md")) {
      this.destPath = destPath;
    } else {
      throw new IllegalArgumentException("Study guide file must be .md");
    }
  }

  /**
   * Gets .md files in this FileCreator's root directory and sorts the .md files by this
   * FileCreator's comparator. Creates a summary of each file in the sorted list
   * and adds it to a building list of AbContent. Creates a new StudyGuide from the list of
   * AbContent and writes the new StudyGuide to the destination path. Throws
   * RuntimeException if file cannot be written.
   */
  public void createStudyGuide() {
    ArrayList<MdFile> files = this.collectMd();
    this.sortFiles(files);

    ArrayList<AbContent> summaries = new ArrayList<>();

    // for each file in the sorted list of files, summarize the file and add the summarized
    // contents to the summaries list
    for (MdFile f : files) {
      FileSummarizer summarizer = new FileSummarizer(f.getPath());
      ArrayList<AbContent> summary = summarizer.createSummary();
      summaries.addAll(summary);
    }

    StudyGuide sd = new StudyGuide(summaries);
    try {
      this.createStudyGuideFile(sd);
    } catch (IOException e) {
      throw new RuntimeException("File cannot be written.");
    }
  }

  /**
   * Creates a new instance of the MarkdownCollector visitor. Calls walkFileTree and creates a
   * list of .md files in this FileCreator's root directory and returns the list.
   * Throws RuntimeException if walkFileTree throws an IOException.
   *
   * @return the list of ordered .md files
   */
  private ArrayList<MdFile> collectMd() {
    ArrayList<MdFile> mdFiles = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();
    MarkdownCollector collector = new MarkdownCollector(mdFiles, files);

    try {
      Files.walkFileTree(this.rootPath, collector);
    } catch (IOException e) {
      throw new RuntimeException("File(s) could not be visited");
    }

    return mdFiles;
  }

  /**
   * Sorts the given list of files by this FileCreator's comparator by creating an instance of the
   * FileSorter class
   *
   * @param mdFiles the list to be sorted
   */
  private void sortFiles(ArrayList<MdFile> mdFiles) {
    FileSorter f = new FileSorter(mdFiles, this.comp);
    f.sort();
  }

  /**
   * Creates the given study guide at this FileCreator's destination path using the java FileWriter.
   * Overwrites the given destination path if it already exists, otherwise it creates the file.
   *
   * @param sd the StudyGuide to be written
   * @throws IOException if IOException is thrown from FileWriter
   */
  private void createStudyGuideFile(StudyGuide sd) throws IOException {
    if (!Files.exists(Path.of(this.destPath))) {
      Files.createDirectories(Path.of(this.destPath).getParent());
    }

    FileWriter writer = new FileWriter(this.destPath);

    ArrayList<String> strings = sd.writeStudyGuide();

    // iterate through the list of strings and write each one; if the first string starts with \n,
    // delete the \n
    for (int i = 0; i < strings.size(); i += 1) {
      String s = strings.get(i);
      if (i == 0 && s.charAt(0) == '\n') {
        s = s.substring(1);
      }
      writer.write(s);
      writer.write("\n");
    }
    writer.close();
  }
}
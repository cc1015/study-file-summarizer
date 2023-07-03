package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a file reader that has a Path and Scanner field that extracts the wanted information
 * from a .md file.
 */
public class FileSummarizer {
  private Path path;
  private Scanner scanner;

  /**
   * The constructor takes in the path as a String, creates a new Path with the String, and
   * creates a Scanner with the Path as the input if the given path exists. If it does not exist,
   * a IllegalArgumentException is thrown.
   *
   * @param path name of path as String
   */
  FileSummarizer(String path) {
    this.path = Path.of(path);

    try {
      this.scanner = new Scanner(this.path);
    } catch (IOException e) {
      throw new IllegalArgumentException("File cannot be accessed");
    }
  }

  /**
   * Scans this FileSummarizer's path file and checks if each line either is a header or is not.
   * If it is, calls parseHeader to create the Header and add it to the list of content. If it is
   * not, the line gets added to a building StringBuilder. Once the next header is reached by the
   * Scanner, the StringBuilder gets parsed by parseInfo, and the returned list of important
   * information is added to the list of content. Once everything has been scanned, return the list
   * of content
   *
   * @return the StudyGuide of the file in this FileSummarizer's path
   */
  public ArrayList<AbContent> createSummary() {
    ArrayList<AbContent> summary = new ArrayList<>();
    StringBuilder content = new StringBuilder();

    // while there is a next line, get the next line and set it to the current line
    // If the current line begins with #, summarize content (the previous non-header information
    // of the file) and add it to summary, parse the current line and save it as a header.
    // Otherwise, save the current line to content.
    while (this.scanner.hasNext()) {
      StringBuilder line = new StringBuilder(scanner.nextLine());
      if (line.length() > 1 && this.isHeaderFormat(line)) {
        summary.addAll(this.parseInfo(content));
        content = new StringBuilder();
        summary.add(this.parseHeader(line));
      } else {
        content.append(line);
      }
    }
    summary.addAll(this.parseInfo(content));
    return summary;
  }

  /**
   * Parses the given StringBuilder and constructs a Header
   *
   * @param s the StringBuilder to be parsed
   * @return a Header produced from parsing the given StringBuilder
   */
  private Header parseHeader(StringBuilder s) {
    StringBuilder header = new StringBuilder();
    int hashCount = 0;

    // while the first char of line is #, increase the indentCount by 1 and delete the just
    // parsed # from line
    while (s.length() > 0 && s.charAt(0) == '#') {
      hashCount += 1;
      s.deleteCharAt(0);
    }

    if (s.length() > 0) {
      s.deleteCharAt(0);
    }

    // while line has characters add the char to the header and delete the just parsed
    // char from content
    while (s.length() > 0) {
      header.append(s.charAt(0));
      s.deleteCharAt(0);
    }

    return new Header(header.toString(), hashCount);
  }

  /**
   * Parses the given StringBuilder. With every [[]] it encounters, saves the text within the
   * brackets and creates a new ImportantInfo of the text and adds the ImportantInfo to a list of
   * ImportantInfo. Once parsing is complete, returns the list of ImportantInfo.
   *
   * @param content the given StringBuilder to be parsed
   * @return list of important information
   */
  private ArrayList<ImportantInfo> parseInfo(StringBuilder content) {
    ArrayList<ImportantInfo> summary = new ArrayList<>();

    // while content has characters, parse it
    while (content.length() > 0) {
      StringBuilder info = new StringBuilder();

      if (content.length() > 1 && (content.charAt(0) == '[') && (content.charAt(1) == '[')) {
        content.deleteCharAt(0);
        content.deleteCharAt(0);

        // content has characters and the first and second chars are not ']', add the first char
        // to info and delete the just parsed char from content
        while (content.length() > 0
            && !((content.charAt(0) == ']') && (content.charAt(1) == ']'))) {
          info.append(content.charAt(0));
          content.deleteCharAt(0);
        }

        if (content.length() > 0) {
          content.deleteCharAt(0);
          content.deleteCharAt(0);

          ImportantInfo i = new ImportantInfo(info.toString());
          summary.add(i);
        }
      } else {
        content.deleteCharAt(0);
      }
    }
    return summary;
  }

  /**
   * Determines if the given StringBuilder is of valid header format:
   * begins with #
   * has no more than 6 #
   * # are consecutive
   * the text of the header follows after a space after the #s
   * a header with no text and only #s is valid (as long as there are 6 or less #s)
   *
   * @param s the StringBuilder to be decided as valid header format
   * @return whether the given StringBuilder is of valid header format
   */
  private boolean isHeaderFormat(StringBuilder s) {
    final int maxHashNum = 6;
    final boolean isOnlyHash = "#".repeat(s.length()).contentEquals(s);

    if (s.charAt(0) != '#') {
      return false;
    } else if (isOnlyHash && s.length() <= maxHashNum) {
      // if the given StringBuilder is only #s and does not exceed the # max number
      return true;
    } else {
      int hashCount = 0;
      // iterates through s and counts the #s in s to make sure it is 6 or less and that there is a
      // space after the #s and before the header text
      for (int i = 0; i < s.length() - 1 && hashCount <= maxHashNum; i += 1) {
        if (s.charAt(i) == '#') {
          hashCount += 1;
          if (s.charAt(i + 1) == ' ') {
            return true;
          }
        }
      }
      return false;
    }
  }
}

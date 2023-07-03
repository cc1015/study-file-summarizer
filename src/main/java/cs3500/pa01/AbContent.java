package cs3500.pa01;

/**
 * Represents content in a .md file; has text content represented as a String field.
 */
public abstract class AbContent {
  private String content;

  /**
   * Constructor sets content to the given String.
   *
   * @param content the given String to be set to this AContent's content.
   */
  AbContent(String content) {
    this.content = content;
  }

  /**
   * Returns this AbContent's content.
   *
   * @return this AbContent's content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Returns this AbContent as a String in the correct format.
   *
   * @return this AbContent as a String in the correct format
   */
  public abstract String writeContent();

  /**
   * Determines whether this AContent is the same as the given AbContent by comparing writeContent
   * output.
   *
   * @param other the other AbContent being compared
   *
   * @return boolean indicating equality
   */
  public boolean sameAbContent(AbContent other) {
    return this.writeContent().equals(other.writeContent());
  }
}

package cs3500.pa01;

/**
 * Represents important information in a study guide file that was surround by [[]] in the original
 * .md files.
 */
public class ImportantInfo extends AbContent {
  /**
   * Constructor calls super constructor to set the content field to the given String.
   *
   * @param content the given String to be set to this ImportantInfo's content
   */
  ImportantInfo(String content) {
    super(content);
  }

  /**
   * Returns this ImportantInfo as a String with "- " before the content.
   *
   * @return this ImportantInfo as a String as it would appear on .md file.
   */
  @Override
  public String writeContent() {
    return "- " + this.getContent();
  }
}

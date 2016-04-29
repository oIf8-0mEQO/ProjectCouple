package group50.coupletones.util;

/**
 * A simple interface that specifies a tag for a particular object.
 * Useful for use as first paramter in Log.d(...)
 * @author Henry Mao
 */
public interface Taggable {
  /**
   * The default method returns the class name.
   * @return The tag as a string
   */
  default String getTag() {
    return this.getClass().getSimpleName();
  }
}

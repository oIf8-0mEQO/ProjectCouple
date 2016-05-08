package group50.coupletones.util.storage;

/**
 * @author Ranlin Huang
 * @since 4/25/16
 */

/**
 * Storable interface that handles
 * storage of the app data.
 */
public interface Storable {

  /**
   * Save User data onto phone
   *
   * @param storage
   */
  void save(Storage storage);

  /**
   * Load User data from phone
   *
   * @param storage
   */
  void load(Storage storage);
}
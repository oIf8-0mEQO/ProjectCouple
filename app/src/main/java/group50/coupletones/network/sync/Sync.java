package group50.coupletones.network.sync;

/**
 * An object that handles real time database syncing for objects.
 *
 * @author Henry Mao
 * @since 5/24/16
 */
public interface Sync {
  /**
   * Hooks listeners to every field in a class annotated with @Syncable.
   *
   * When the database for this field changes, the field itself will be automatically updated.
   *
   * @return The Sync object instance
   */
  Sync subscribeAll();

  /**
   * Subscribes a field in the class to receive updates from the database.
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  Sync subscribe(String fieldName);

  /**
   * Attempts to sync a specific field to the server
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  Sync update(String fieldName);
}

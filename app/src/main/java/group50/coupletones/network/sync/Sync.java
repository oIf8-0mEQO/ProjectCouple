package group50.coupletones.network.sync;

import group50.coupletones.util.observer.Properties;

/**
 * @author Henry Mao
 * @since 5/27/16
 */
public interface Sync {

  /**
   * Hooks listeners to every field in a class annotated with @Watch.
   * <p>
   * When the database for this field changes, the field itself will be automatically updated.
   *
   * @return The Properties object instance
   */
  Properties subscribeAll();

  /**
   * Subscribes a field in the class to receive updates from the database automatically.
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  Properties subscribe(String fieldName);

  /**
   * Attempts to sync a specific field to the server
   *
   * @param fieldName The name of the field to publish
   * @return Self instance
   */
  Properties publish(String fieldName);

  /**
   * Publishes all fields to the database.
   *
   * @return Self instance
   */
  Properties publishAll();

  /**
   * The child observer, by name
   *
   * @param child The child
   * @return The child observer
   */
  Properties child(String child);

  /**
   * @return The parent observer
   */
  Properties parent();
}

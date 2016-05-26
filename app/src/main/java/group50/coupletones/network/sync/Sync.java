package group50.coupletones.network.sync;

import com.google.firebase.database.DatabaseReference;
import rx.Observable;

/**
 * An object that handles real time database syncing for objects.
 *
 * @author Henry Mao
 * @since 5/24/16
 */
public interface Sync {

  Sync child(String child);

  /**
   * Sets the Sync object to watch a particular object
   *
   * @param obj The object to watch
   * @return Self instance
   */
  Sync watch(Object obj);

  /**
   * @return Reference to the database this object is synced with
   */
  DatabaseReference getRef();

  /**
   * Sets the Sync object to refer a particular database
   *
   * @param ref The database use
   * @return Self instance
   */
  Sync setRef(DatabaseReference ref);

  /**
   * Gets the observable associated with this field.
   *
   * @param fieldName The name of the field in this class.
   * @return An observable object.
   */
  Observable<?> get(String fieldName);

  /**
   * Hooks listeners to every field in a class annotated with @Syncable.
   *
   * When the database for this field changes, the field itself will be automatically updated.
   *
   * @return The Sync object instance
   */
  Sync subscribeAll();

  /**
   * Subscribes a field in the class to receive updates from the database automatically.
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  Sync subscribe(String fieldName);

  /**
   * Attempts to sync a specific field to the server
   *
   * @param fieldNames The names of the field to publish
   * @return Self instance
   */
  Sync publish(String... fieldNames);

  /**
   * Publishes all fields to the database.
   *
   * @return Self instance
   */
  Sync publishAll();
}

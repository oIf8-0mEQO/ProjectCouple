package group50.coupletones.network.sync;

/**
 * An object that composes a Properties object.
 * Properties uses this to recursively serializes data to child data. (Like a Tree)
 *
 * @author Henry Mao
 * @since 5/27/16
 */
public interface SyncProvider {

  /**
   * Gets the sync object responsible for syncing this object
   *
   * @return The sync object.
   */
  Sync getSync();
}

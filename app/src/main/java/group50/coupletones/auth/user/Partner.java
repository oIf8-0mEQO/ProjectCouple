package group50.coupletones.auth.user;

/**
 * @author Brandon Chi
 * @since  5/5/2016
 */

import group50.coupletones.network.sync.Sync;
import group50.coupletones.network.sync.Syncable;

/**
 * Represents a Partner that a User
 * connects with through the app.
 */
public class Partner implements User {
  /**
   * Object responsible for syncing the object with database
   */
  private final Sync sync;
  @Syncable
  private String name;
  @Syncable
  private String email;

  /**
   * @param sync The object handling synchronizing partner data
   */
  public Partner(Sync sync) {
    this.sync = sync.watch(this).subscribeAll();
  }

  /**
   * @return The email of the partner
   */
  @Override
  public String getId() {
    return getEmail();
  }

  /**
   * @return The name of the partner
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The email of the partner
   */
  @Override
  public String getEmail() {
    return email;
  }
}

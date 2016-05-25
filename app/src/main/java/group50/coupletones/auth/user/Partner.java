package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.sync.Sync;

import java.util.List;

/**
 * Represents a Partner that a User.
 * Delegates all methods to ConcreteUser, but prevents modification
 * to fields.
 *
 * @author Brandon Chi
 * @since 5/5/2016
 */
public class Partner implements User {

  private final ConcreteUser user;

  /**
   * @param sync The object handling synchronizing partner data
   */
  public Partner(Sync sync) {
    user = new ConcreteUser(sync);
  }

  @Override
  public String getId() {
    return user.getId();
  }

  @Override
  public String getName() {
    return user.getName();
  }

  @Override
  public String getEmail() {
    return user.getEmail();
  }

  @Override
  public List<FavoriteLocation> getFavoriteLocations() {
    return user.getFavoriteLocations();
  }
}

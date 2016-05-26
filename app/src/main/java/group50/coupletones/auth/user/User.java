/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.ObservableProvider;

import java.util.List;

/**
 * The user interface represents a single user in the app.
 */
public interface User extends ObservableProvider {
  /**
   * @return The id of the user
   */
  String getId();

  /**
   * @return The name of the user
   */
  String getName();

  /**
   * @return The email of the user
   */
  String getEmail();

  /**
   * @return The partner of the user
   */
  User getPartner();

  /**
   * Provides an immutable list. Use add and remove to modify locations. Never null.
   * @return The list of the users favorite locations.
   */
  List<FavoriteLocation> getFavoriteLocations();
}

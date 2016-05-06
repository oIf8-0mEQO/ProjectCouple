/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth;

import group50.coupletones.map.FavoriteLocation;

import java.util.List;

/**
 * The user interface represents a single user in the app.
 */
public interface User {
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
   * @Return The a list of the users favoite locations.
   */
  List<FavoriteLocation> getFavoriteLocations();
}

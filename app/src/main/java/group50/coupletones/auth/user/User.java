/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.util.properties.PropertiesProvider;
import rx.Observable;

import java.util.List;

/**
 * Represents a user
 */
public interface User extends PropertiesProvider {
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
   * Provides an immutable list. Use add and remove to modify locations. Never null.
   *
   * @return The list of the users favorite locations.
   */
  List<FavoriteLocation> getFavoriteLocations();

  /**
   * @return The list of the user's visited locations.
   */
  List<VisitedLocationEvent> getVisitedLocations();

  /**
   * @return An observable that completes after user is fully loaded from database.
   */
  Observable<User> load();
}

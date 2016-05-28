/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user.behavior;

import com.google.firebase.database.GenericTypeIndicator;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.PropertiesProvider;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the behavior of user's profile. Strategy pattern.
 */
public class ProfileBehavior implements PropertiesProvider {

  /**
   * Object responsible for syncing the object with database
   */
  private final Properties properties;

  private String id;
  /**
   * Name of the user
   */
  private String name;
  /**
   * Email of the user
   */
  private String email;
  /**
   * The user's list of favorite location.
   */
  private List<FavoriteLocation> favoriteLocations = new LinkedList<>();
  /**
   * The user's list of visited locations.
   */
  private List<VisitedLocationEvent> visitedLocations = new LinkedList<>();


  /**
   * Creates a ConcreteUser
   */
  public ProfileBehavior(Properties properties) {
    //TODO: Use DI
    this.properties = properties
      .property("id").bind(this)
      .property("name").bind(this)
      .property("email").bind(this)
      .property("favoriteLocations")
      .mark(new GenericTypeIndicator<List<FavoriteLocation>>() {
      })
      .bind(this);
  }

  /**
   * @return The id of the user
   */
  public String getId() {
    return id;
  }

  /**
   * @return The name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * @return The email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return The list of favorite locations of the user
   */
  public List<FavoriteLocation> getFavoriteLocations() {
    return favoriteLocations != null ? Collections.unmodifiableList(favoriteLocations) : Collections.emptyList();
  }

  /**
   * @return The list of visited locations of the user
   */
  public List<VisitedLocationEvent> getVisitedLocations() {
    return visitedLocations != null ? Collections.unmodifiableList(visitedLocations) : Collections.emptyList();
  }

  /**
   * Adds a favorite location
   *
   * @param location The location to add
   */
  public void addFavoriteLocation(FavoriteLocation location) {
    if (favoriteLocations == null)
      favoriteLocations = new LinkedList<>();
    favoriteLocations.add(location);

    properties
      .property("favoriteLocations")
      .update();
  }

  /**
   * Adds a visited location
   * @param visitedLocation The visited location to add.
   */
  public void addVisitedLocation(VisitedLocationEvent visitedLocation) {
    if (visitedLocations == null)
      visitedLocations = new LinkedList<>();
    visitedLocations.add(visitedLocation);

    properties
        .property("visitedLocations")
        .set(visitedLocations);
  }


  /**
   * Removes a favorite location
   *
   * @param location The location to remove
   */
  public void removeFavoriteLocation(FavoriteLocation location) {
    if (favoriteLocations != null) {
      favoriteLocations.remove(location);

      properties
        .property("favoriteLocations")
        .set(favoriteLocations);
    }
  }

  @Override
  public Properties getProperties() {
    return properties;
  }
}

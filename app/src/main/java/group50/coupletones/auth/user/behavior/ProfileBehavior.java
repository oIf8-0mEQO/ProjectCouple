/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user.behavior;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
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
   * Creates a ConcreteUser
   */
  public ProfileBehavior(Properties properties) {
    //TODO: Use DI
    this.properties = properties
      .property("id").bind(this)
      .property("name").bind(this)
      .property("email").bind(this)
      .property("favoriteLocations").bind(this);
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

  public List<FavoriteLocation> getFavoriteLocations() {
    return favoriteLocations != null ? Collections.unmodifiableList(favoriteLocations) : Collections.emptyList();
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
      .set(favoriteLocations);
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

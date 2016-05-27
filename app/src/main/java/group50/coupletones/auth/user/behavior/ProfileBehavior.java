/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user.behavior;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.observer.ConcreteProperties;
import group50.coupletones.util.observer.Properties;
import group50.coupletones.util.observer.PropertiesProvider;

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
   *
   * @param sync The sync object, with a database reference for this user.
   */
  public ProfileBehavior(Sync sync) {
    this.properties = new ConcreteProperties(this)
      .property("id").bind()
      .property("name").bind()
      .property("email").bind()
      .property("favoriteLocations").bind();
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
    sync.publish("favoriteLocations");
  }

  /**
   * Removes a favorite location
   *
   * @param location The location to remove
   */
  public void removeFavoriteLocation(FavoriteLocation location) {
    if (favoriteLocations != null) {
      favoriteLocations.remove(location);
      sync.publish("favoriteLocations");
    }
  }

  @Override
  public Properties getProperties() {
    return properties;
  }
}

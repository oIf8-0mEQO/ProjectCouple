/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user.behavior;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.network.sync.Syncable;
import group50.coupletones.util.ObservableProvider;
import rx.Observable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the behavior of user's profile. Strategy pattern.
 */
public class ProfileBehavior implements ObservableProvider {

  /**
   * Object responsible for syncing the object with database
   */
  private final Sync sync;

  @Syncable
  private String id;
  /**
   * Name of the user
   */
  @Syncable
  private String name;
  /**
   * Email of the user
   */
  @Syncable
  private String email;
  /**
   * The user's list of favorite location.
   */
  @Syncable
  private List<FavoriteLocation> favoriteLocations = new LinkedList<>();
  /**
   * The user's list of visited locations.
   */
  @Syncable
  private List<VisitedLocationEvent> visitedLocations = new LinkedList<>();


  /**
   * Creates a ConcreteUser
   * @param sync The sync object, with a database reference for this user.
   */
  public ProfileBehavior(Sync sync) {
    this.sync = sync.watch(this).subscribeAll();
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
   * @param location The location to add
   */
  public void addFavoriteLocation(FavoriteLocation location) {
    favoriteLocations.add(location);
    sync.publish("favoriteLocations");
  }

  /**
   * Adds a visited location
   * @param visitedLocation The visited location to add.
   */
  public void addVisitedLocation(VisitedLocationEvent visitedLocation) {
    visitedLocations.add(visitedLocation);
    sync.publish("visitedLocations");
  }


  /**
   * Removes a favorite location
   * @param location The location to remove
   */
  public void removeFavoriteLocation(FavoriteLocation location) {
    favoriteLocations.remove(location);
    sync.publish("favoriteLocations");
  }

  public <T> Observable<T> getObservable(String name) {
    return sync.getObservable(name);
  }
}

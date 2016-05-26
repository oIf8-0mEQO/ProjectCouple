/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.network.sync.Syncable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a User logged in via Google sign in.
 * Uses data from the GoogleSignInAccount object.
 * <p>
 * Firebase
 */
public class ConcreteUser implements LocalUser {

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
   * User's partner
   */
  private User partner;
  /**
   * The user's list of favorite location.
   */
  @Syncable
  private List<FavoriteLocation> favoriteLocations = new LinkedList<>();

  /**
   * The ID of the user's partner
   */
  @Syncable
  private String partnerId;
  /**
   * A list of all partner Ids who is trying to request partnership
   * with this user.
   */
  @Syncable
  private List<String> partnerRequests = new LinkedList<>();

  /**
   * Creates a ConcreteUser
   *
   * @param sync The sync object, with a database reference for this user.
   */
  //TODO: DI?
  public ConcreteUser(Sync sync) {
    this.sync = sync
      .watch(this)
      .subscribeAll();
  }

  /**
   * @return The id of the user
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * @return The name of the user
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The email of the user
   */
  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public List<FavoriteLocation> getFavoriteLocations() {
    return favoriteLocations != null ? Collections.unmodifiableList(favoriteLocations) : Collections.emptyList();
  }

  /**
   * Adds a favorite location
   *
   * @param location The location to add
   */
  @Override
  public void addFavoriteLocation(FavoriteLocation location) {
    favoriteLocations.add(location);
    sync.publish("favoriteLocations");
  }

  /**
   * Removes a favorite location
   *
   * @param location The location to remove
   */
  @Override
  public void removeFavoriteLocation(FavoriteLocation location) {
    favoriteLocations.remove(location);
    sync.publish("favoriteLocations");
  }

  /**
   * @return The partner of the user
   */
  @Override
  public User getPartner() {
    // Lazy initialize the partner from Id
    if (partnerId != null) {
      // An update has occurred. Attempt to reconstruct the partner object.
      partner = new Partner(sync.child(partnerId));
      partnerId = null;
    }

    return partner;
  }

  /**
   * Sets partner
   *
   * @param partnerId The partner's ID to set
   */
  @Override
  public void setPartner(String partnerId) {
    this.partnerId = partnerId;
    sync.publish("partnerId");
  }

  /**
   * Requests to partner with this user.
   *
   * @param requester The user sending the request
   */
  @Override
  public void requestPartner(User requester) {
    partnerRequests.add(requester.getId());
    sync.publish("partnerRequests");
  }

  /**
   * @return A list of partner ids requesting to be partner with this user
   */
  @Override
  public List<String> getPartnerRequests() {
    return partnerRequests != null ? Collections.unmodifiableList(partnerRequests) : Collections.emptyList();
  }

  public Sync getSync() {
    return sync;
  }
}

package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import rx.Observable;

/**
 * @author Brandon Chi
 * @since 5/5/2016
 */

/**
 * Represents the local user.
 */
public interface LocalUser extends User {

  /**
   * Adds a favorite location
   * @param location The location to add
   */
  void addFavoriteLocation(FavoriteLocation location);

  /**
   * Adds a visited location
   * @param visitedLocation The location to add
   */
  void addVisitedLocation(VisitedLocationEvent visitedLocation);

  /**
   * Removes a favorite location
   * @param location The location to remove
   */
  void removeFavoriteLocation(FavoriteLocation location);

  /**
   * Requests to partner with this user.
   * @param requester The user sending the request
   */
  void requestPartner(User requester);

  /**
   * Handles the partner request, either accepting or rejecting it
   * @param partnerId The partner ID
   * @param accept True if accept, false if reject
   */
  void handlePartnerRequest(String partnerId, boolean accept);

  /**
   * @return The partner of the user
   */
  User getPartner();

  /**
   * Sets the partner
   * @param id The partner's user id to set
   */
  void setPartner(String id);

  /**
   * @return An observable that notifies when the partner changes.
   */
  Observable<User> getPartnerObservable();
}

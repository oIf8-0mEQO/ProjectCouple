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
   * @return Global notifications setting
   */
  Boolean getGlobalNotificationsSetting();

  /**
   * This function turns on/off the global notifications setting
   * @return globalNotificationsAreOn true if notifications are on, false if turned off
   */
  Boolean setGlobalNotificationsSetting(Boolean setting);

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
   * Gets the partner of the local user.
   * The partner could be null, if this user does not have a partner.
   * The Observable completes when the partner object is fully loaded.
   * @return The observable of the user's partner.
   */
  Observable<Partner> getPartner();

  /**
   * Sets the partner
   * @param id The partner's user id to set
   */
  void setPartner(String id);
}

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
  boolean getGlobalNotificationsSetting();

  /**
   * @return Tones setting
   */
  boolean getTonesSetting();

  /**
   * @return Vibration setting
   */
  boolean getVibrationSetting();

  /**
   * This function turns on/off the global notifications
   * @return globalNotificationsAreOn true if notifications are on, false if turned off
   */
  boolean setGlobalNotificationsSetting(boolean setting);

  /**
   * This function turns on/off the tones
   * @return tonesAreOn true if notifications are on, false if turned off
   */
  boolean setTonesSetting(boolean setting);

  /**
   * This function turns on/off the vibration
   * @return vibrationIsOn true if notifications are on, false if turned off
   */
  boolean setVibrationSetting(boolean setting);

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

  /**
   * Updates the cooldown of the passed FavoriteLocation
   */
  public void updateCooldownOfFavorite(FavoriteLocation location);
}

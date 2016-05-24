package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.storage.Storable;

import java.util.List;

/**
 * @author Brandon Chi
 * @since  5/5/2016
 */

/**
 * The Local User interface represents
 * a local user in the app.
 */
public interface LocalUser extends User, Storable {

  /**
   * @return The partner of the user
   */
  User getPartner();

  /**
   * Sets the partner
   * @param partner The partner to set
   */
  void setPartner(User partner);

  /**
   * @return The a list of the users favoite locations.
   */
  List<FavoriteLocation> getFavoriteLocations();
}

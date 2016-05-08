package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.storage.Storable;

import java.util.List;

/**
 * Created by brandon on 5/5/2016.
 */
public interface LocalUser extends User, Storable {

  /**
   * @return The partner of the user
   */
  User getPartner();

  /**
   * @param partner
   */
  void setPartner(Partner partner);

  /**
   * @return The a list of the users favoite locations.
   */
  List<FavoriteLocation> getFavoriteLocations();
}

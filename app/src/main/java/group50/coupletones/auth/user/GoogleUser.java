/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import group50.coupletones.controller.tab.favoritelocations.map.location.UserFavoriteLocation;
import group50.coupletones.util.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User logged in via Google sign in.
 * Wraps the GoogleSignInAccount object.
 */
public class GoogleUser implements LocalUser {

  private final GoogleSignInAccount account;
  private List<UserFavoriteLocation> favoriteLocations;
  private User partner;

  /**
   * Creates a GoogleUser
   *
   * @param account The Google sign in account object
   */
  public GoogleUser(GoogleSignInAccount account) {
    this.account = account;
    favoriteLocations = new ArrayList<>();
  }

  /**
   * @return The id of the user
   */
  @Override
  public String getId() {
    return account.getId();
  }

  /**
   * @return The name of the user
   */
  @Override
  public String getName() {
    return account.getDisplayName();
  }

  /**
   * @return The email of the user
   */
  @Override
  public String getEmail() {
    return account.getEmail();
  }

  @Override
  public List<UserFavoriteLocation> getFavoriteLocations() {
    return favoriteLocations;
  }

  /**
   * @return The partner of the user
   */
  @Override
  public User getPartner() {
    return partner;
  }

  /**
   * Sets partner
   *
   * @param partner
   */
  @Override
  public void setPartner(User partner) {
    this.partner = partner;
  }

  /**
   * Save User data onto phone
   * @param storage
   */
  @Override
  public void save(Storage storage) {
    if (getPartner() != null) {
      storage.setString("partnerName", getPartner().getName());
      storage.setString("partnerEmail", getPartner().getEmail());
    } else {
      storage.delete("partnerName");
      storage.delete("partnerEmail");
    }

    storage.setBoolean("hasPartner", getPartner() != null);

    storage.setCollection("favoriteLocations", favoriteLocations);
  }

  /**
   * Load User data from phone
   * @param storage
   */
  @Override
  public void load(Storage storage) {
    if (storage.contains("hasPartner") && storage.getBoolean("hasPartner")) {
      String name = storage.getString("partnerName");
      String email = storage.getString("partnerEmail");
      Partner partner = new Partner(name, email);
      setPartner(partner);
    } else {
      setPartner(null);
    }

    favoriteLocations = storage.getCollection("favoriteLocations", UserFavoriteLocation::new);//TODO: Fix this.
  }
}

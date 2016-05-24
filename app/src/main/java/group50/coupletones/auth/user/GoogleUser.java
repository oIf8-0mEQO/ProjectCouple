/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.FirebaseDatabase;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.network.sync.FirebaseSync;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.network.sync.Syncable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User logged in via Google sign in.
 * Uses data from the GoogleSignInAccount object.
 * <p>
 * Firebase
 */
public class GoogleUser implements LocalUser {

  /**
   * Object responsible for syncing the object with Firebase
   */
  private final Sync sync;
  /**
   * ID of the user
   */
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
   * The ID of the user's partner
   */
  @Syncable
  private String partnerId;

  /**
   * The user's list of favorite location.
   */
  @Syncable
  private List<FavoriteLocation> favoriteLocations;

  /**
   * Creates a GoogleUser
   *
   * @param account The Google sign in account object
   */
  public GoogleUser(GoogleSignInAccount account) {
    sync = new FirebaseSync(this, FirebaseDatabase.getInstance().getReference("users/" + id)).subscribeAll();

    id = account.getId();
    name = account.getDisplayName();
    email = account.getEmail();
    favoriteLocations = new ArrayList<>();

    sync.publish("id", "name", "email", "favoriteLocations");
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
   * @param partner The partner to set
   */
  @Override
  public void setPartner(User partner) {
    this.partner = partner;
    sync.publish("partnerId");
  }
}

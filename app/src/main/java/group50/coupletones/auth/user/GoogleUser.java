/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user;

import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import group50.coupletones.controller.tab.favoritelocations.map.location.UserFavoriteLocation;
import group50.coupletones.util.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a User logged in via Google sign in.
 * Uses data from the GoogleSignInAccount object.
 * <p>
 * Firebase
 */
public class GoogleUser implements LocalUser {

  /**
   * ID of the user
   */
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
   * User's partner
   */
  private User partner;

  /**
   * The user's list of favorite location.
   */
  private List<UserFavoriteLocation> favoriteLocations;

  public GoogleUser() {
  }

  /**
   * Creates a GoogleUser
   *
   * @param account The Google sign in account object
   */
  public GoogleUser(GoogleSignInAccount account) {
    id = account.getId();
    name = account.getDisplayName();
    email = account.getEmail();
    favoriteLocations = new ArrayList<>();
    //TOOD: remove this save?
    save();
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
    save();
  }


  //TODO: Refactor
  public void save() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    myRef.child(getId()).setValue(toMap());
  }

  public Map<String, Object> toMap() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", name);
    map.put("email", email);
    if (getPartner() != null)
      map.put("partnerId", getPartner().getId());
    return map;
  }

  /**
   * Save User data onto phone
   *
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
   *
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

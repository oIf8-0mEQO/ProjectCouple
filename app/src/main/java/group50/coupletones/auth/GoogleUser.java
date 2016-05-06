/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import group50.coupletones.map.FavoriteLocation;

/**
 * Represents a User logged in via Google sign in.
 * Wraps the GoogleSignInAccount object.
 */
public class GoogleUser implements User {

  private final GoogleSignInAccount account;
  private List<FavoriteLocation> favoriteLocations;

  /**
   * Creates a GoogleUser
   *
   * @param account The Google sign in account object
   */
  public GoogleUser(GoogleSignInAccount account)
  {
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
  public List<FavoriteLocation> getFavoriteLocations()
  {
    return favoriteLocations;
  }
}

/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import group50.coupletones.util.storage.Storage;

/**
 * Represents a User logged in via Google sign in.
 * Wraps the GoogleSignInAccount object.
 */
public class GoogleUser implements LocalUser {

  private final GoogleSignInAccount account;
  private User partner;

  /**
   * Creates a GoogleUser
   *
   * @param account The Google sign in account object
   */
  public GoogleUser(GoogleSignInAccount account) {
    this.account = account;
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
  public void setPartner(Partner partner) {
    this.partner = partner;
  }

  /**
   * Save User data onto phone
   */
  @Override
  public void save(Storage s) {
    if (getPartner() != null) {
      s.setString("partnerName", getPartner().getName());
      s.setString("partnerEmail", getPartner().getEmail());
    }

    s.setBoolean("hasPartner", getPartner() != null);

    // TODO: Implement Save FaveLocations
  }

  /**
   * Load User data from phone
   */
  @Override
  public void load(Storage s) {
    if (s.contains("hasPartner") && s.getBoolean("hasPartner")) {
      String name = s.getString("partnerName");
      String email = s.getString("partnerEmail");
      Partner partner = new Partner(name, email);
      setPartner(partner);
    } else {
      setPartner(null);
    }

    // TODO: Implement Load FaveLocations

  }
}

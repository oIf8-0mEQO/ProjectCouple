package group50.coupletones.auth.user;

/**
 * @author Brandon Chi
 * @since  5/5/2016
 */

/**
 * Represents a Partner that a User
 * connects with through the app.
 */
public class Partner implements User {
  private final String name;
  private final String email;

  /**
   * @param name Partner's Name
   * @param email Parnter's Email
   */
  public Partner(String name, String email) {
    this.name = name;
    this.email = email;
  }

  /**
   * @return The email of the partner
   */
  @Override
  public String getId() {
    return getEmail();
  }

  /**
   * @return The name of the partner
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The email of the partner
   */
  @Override
  public String getEmail() {
    return email;
  }
}

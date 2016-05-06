package group50.coupletones.auth.user;

/**
 * Created by brandon on 5/5/2016.
 */
public class Partner implements User {
  String name, email;

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

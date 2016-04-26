/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth;

/**
 * The user represents a single user in the app.
 */
public abstract class User {
  /**
   * @return The id of the user
   */
  public abstract String getId();

  /**
   * @return The name of the user
   */
  public abstract String getName();

  /**
   * @return The email of the user
   */
  public abstract String getEmail();
}

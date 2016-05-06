package group50.coupletones.auth;

/**
 * Created by brandon on 5/5/2016.
 */
public interface LocalUser extends User {

  /**
   * @return The id of the user
   */
  String getId();

  /**
   * @return The name of the user
   */
  String getName();

  /**
   * @return The email of the user
   */
  String getEmail();

  /**
   * @return The partner of the user
   */
  User getPartner();


}

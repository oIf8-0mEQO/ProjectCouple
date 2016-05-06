package group50.coupletones.auth;

/**
 * Created by brandon on 5/5/2016.
 */
public interface LocalUser extends User {

  /**
   * @return The partner of the user
   */
  User getPartner();

  /**
   * @return The partner of the user
   */
  void setPartner(Partner partner);


}

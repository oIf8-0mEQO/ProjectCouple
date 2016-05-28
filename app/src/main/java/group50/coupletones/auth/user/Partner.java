package group50.coupletones.auth.user;

/**
 * Represents the Partner as a user object
 *
 * @author Henry Mao
 */
public interface Partner extends User {

  String getPartnerId();

  void setPartnerId(String partnerId);

  void requestPartner(User requester);
}

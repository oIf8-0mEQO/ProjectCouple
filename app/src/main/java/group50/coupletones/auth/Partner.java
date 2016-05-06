package group50.coupletones.auth;

/**
 * Created by brandon on 5/5/2016.
 */
public class Partner implements User {
  String namePartner, emailPartner;

  public Partner(String name, String email) {
    namePartner = name;
    emailPartner = email;
  }

    /**
     * @return null
     */
    @Override
    public String getId() { return null; }

    /**
     * @return The name of the partner
     */
    @Override
    public String getName() { return namePartner; }

    /**
     * @return The email of the partner
     */
    @Override
    public String getEmail() { return emailPartner; }
}

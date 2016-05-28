package group50.coupletones.auth.user.behavior;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.PropertiesProvider;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Provides the behavior for handling the local user's partner
 * @author Henry Mao
 */
public class PartnerBehavior implements PropertiesProvider {
  /**
   * Object responsible for syncing the object with database
   */
  private final Properties properties;

  private final PartnerRequestBehavior requestBehavior;

  /**
   * A subject that can be watched. Helps notify partner change.
   */
  private BehaviorSubject<User> partnerSubject = BehaviorSubject.create();

  /**
   * User's partner
   */
  private Partner partner;

  private String partnerId;

  public PartnerBehavior(Properties properties, PartnerRequestBehavior requestBehavior) {
    this.properties = properties
      .property("partnerId", String.class)
      .setter(this::resetPartner)                             // Sets the partner object based on ID.
      .getter(() -> partnerId) // Gets the partner ID.
      .bind();

    this.requestBehavior = requestBehavior;
  }

  /**
   * @return The partner of the user
   */
  public Partner getPartner() {
    return partner;
  }

  /**
   * Sets partner
   * @param partnerId The partner's ID to set
   */
  public void setPartner(String partnerId) {
    properties.property("partnerId").set(partnerId);
    properties.property("partnerId").update();
  }

  /**
   * Handles the partner request, either accepting or rejecting it
   * @param partnerId The partner ID
   * @param accept True if accept, false if reject
   */
  public void handlePartnerRequest(String partnerId, boolean accept) {
    requestBehavior.removeRequest(partnerId);
    if (accept) {
      setPartner(partnerId);
      //TODO: Two way adding?
    }
  }

  /**
   * Lazy initialize or destroy partner from ID
   */
  private void resetPartner(String partnerId) {
    this.partnerId = partnerId;
    if (partnerId != null) {
      // An update has occurred. Attempt to reconstruct the partner object.
      if (partner == null || !partnerId.equals(partner.getId())) {
        // Partner has changed
        partner = CoupleTones
          .instanceComponentBuilder()
          .build()
          .userFactory()
          .withId(partnerId)
          .build();

        partnerSubject.onNext(partner);
      }
    } else if (partner != null) {
      partner = null;
      partnerSubject.onNext(null);
    }
  }

  public Observable<User> getPartnerObservable() {
    return partnerSubject.startWith(partner);
  }

  @Override
  public Properties getProperties() {
    return properties;
  }
}

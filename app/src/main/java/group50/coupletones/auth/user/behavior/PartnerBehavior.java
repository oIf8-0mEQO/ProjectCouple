package group50.coupletones.auth.user.behavior;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.PropertiesProvider;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Provides the behavior for handling the local user's partner
 *
 * @author Henry Mao
 */
public class PartnerBehavior implements PropertiesProvider {
  /**
   * Object responsible for syncing the object with database
   */
  private final Properties properties;

  private final PartnerRequestBehavior requestBehavior;

  private final LocalUser localUser;

  /**
   * A subject that can be watched. Helps notify partner change.
   */
  private BehaviorSubject<User> partnerSubject = BehaviorSubject.create();

  /**
   * User's partner
   */
  private Partner partner;

  private String partnerId;

  public PartnerBehavior(Properties properties, LocalUser localUser, PartnerRequestBehavior requestBehavior) {
    this.properties = properties
      .property("partnerId", String.class)
      .setter(this::resetPartner)                             // Sets the partner object based on ID.
      .getter(() -> partnerId) // Gets the partner ID.
      .bind();

    this.localUser = localUser;

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
   *
   * @param partnerId The partner's ID to set
   */
  public void setPartner(String partnerId) {
    properties.property("partnerId").set(partnerId);
    properties.property("partnerId").update();
  }

  /**
   * Handles the partner request, either accepting or rejecting it
   *
   * @param partnerId The partner ID
   * @param accept    True if accept, false if reject
   */
  public void handlePartnerRequest(String partnerId, boolean accept) {
    requestBehavior.removeRequest(partnerId);
    if (accept) {
      // Two way partnering
      setPartner(partnerId);
      // Wait for partner to load
      partnerSubject
        .first()
        .filter(p -> p != null)
        .subscribe(partner -> {
          // Set partner id to this partner.
          partner
            .getProperties()
            .property("partnerId")
            .set(localUser.getId());
          // Send update
          partner
            .getProperties()
            .property("partnerId")
            .update();
        });
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

        partner
          .load()
          .subscribe(user -> {
            Partner partner = (Partner) user;
            if (!localUser.getId().equals(partner.getPartnerId())) {
              // Cancel out one way partnerships
              this.partnerId = null;
              this.partner = null;
              partnerSubject.onNext(null);
            } else {
              partnerSubject.onNext(partner);
            }
          });
      }
    } else if (partner != null) {
      if (localUser.getId().equals(partner.getPartnerId())) {
        // Remove partner
        partner.getProperties()
          .property("partnerId")
          .set(null);

        // Send update
        partner
          .getProperties()
          .property("partnerId")
          .update();
      }

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

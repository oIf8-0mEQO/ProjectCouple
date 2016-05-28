package group50.coupletones.auth.user.behavior;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
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
   * User's partner cache
   */
  private BehaviorSubject<Partner> partnerCache = BehaviorSubject.create();

  private String partnerId;

  public PartnerBehavior(Properties properties, LocalUser localUser, PartnerRequestBehavior requestBehavior) {
    this.properties = properties
      .property("partnerId", String.class)
      .setter(newId -> {
        partnerId = newId;

        // Check last partner cache.
        if (partnerId != null) {
          // Partner has changed
          CoupleTones
            .instanceComponentBuilder()
            .build()
            .userFactory()
            .withId(partnerId)
            .build()
            .load()
            .subscribe(x -> partnerCache.onNext((Partner) x));
        } else {
          partnerCache.onNext(null);
        }
      })
      .getter(() -> partnerId)
      .bind(this);

    this.localUser = localUser;

    this.requestBehavior = requestBehavior;
  }

  /**
   * Partner may not have been loaded.
   *
   * @return The partner of the user.
   */
  public Observable<Partner> getPartner() {
    return partnerCache;
  }

  /**
   * Sets partner
   *
   * @param partnerId The partner's ID to set
   */
  public void setPartner(String partnerId) {
/*
    if (this.partnerId != null &&
      partnerId == null &&
      this.partner != null &&
      this.localUser.getId().equals(this.partner.getPartnerId())) {
      Log.d("PartnerBehavior", "Removing partner's id");
      // Remove partner's partnerId
      partner
        .getProperties()
        .property("partnerId")
        .set(null);

      // Send update
      partner
        .getProperties()
        .property("partnerId")
        .update();
    }*/
    this.partnerId = partnerId;
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
      getPartner()
        .filter(p -> p != null)
        .first()
        .subscribe(partner -> {
          // Set partner id to this partner.
          partner
            .getProperties()
            .property("partnerId")
            .set(localUser.getId())
            .update();
        });
    }
  }

  @Override
  public Properties getProperties() {
    return properties;
  }
}

package group50.coupletones.auth.user.behavior;

import android.util.Log;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.util.observer.ObservableProvider;
import group50.coupletones.util.observer.Properties;
import group50.coupletones.util.observer.Watch;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Provides the behavior for handling the local user's partner
 * @author Henry Mao
 */
public class PartnerBehavior implements ObservableProvider {
  /**
   * Object responsible for syncing the object with database
   */
  private final Properties sync;

  private final PartnerRequestBehavior requestBehavior;

  /**
   * A subject that can be watched. Helps notify partner change.
   */
  private BehaviorSubject<User> partnerSubject = BehaviorSubject.create();

  /**
   * The ID of the user's partner
   */
  @Watch
  private String partnerId;

  /**
   * User's partner
   */
  private Partner partner;

  public PartnerBehavior(Properties sync, PartnerRequestBehavior requestBehavior) {
    this.sync = sync.set(this).subscribeAll();
    this.requestBehavior = requestBehavior;

    // Update the Partner object when partnerId changes
    this.sync
      .getObservable("partnerId", String.class)
      .distinctUntilChanged()
      .subscribe(this::resetPartner);
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
    this.partnerId = partnerId;
    sync.publish("partnerId");
  }

  /**
   * Handles the partner request, either accepting or rejecting it
   * @param partnerId The partner ID
   * @param accept True if accept, false if reject
   */
  public void handlePartnerRequest(String partnerId, boolean accept) {
    if (accept) {
      setPartner(partnerId);
    }

    requestBehavior.removeRequest(partnerId);
  }

  /**
   * Lazy initialize or destroy partner from ID
   */
  private void resetPartner(String partnerId) {
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
        Log.d("ConcreteUser", this + " Notify " + partnerId);
      }
    } else if (partner != null) {
      partner = null;
      partnerSubject.onNext(null);
      Log.d("ConcreteUser", this + " Notify " + partnerId);
    }
  }

  public Observable<User> getPartnerObservable() {
    return partnerSubject.startWith(partner);
  }

  @Override
  public <T> Observable<T> getObservable(String name) {
    return sync.getObservable(name);
  }
}

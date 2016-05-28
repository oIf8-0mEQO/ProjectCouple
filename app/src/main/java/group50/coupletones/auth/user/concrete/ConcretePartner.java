package group50.coupletones.auth.user.concrete;

import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.behavior.PartnerRequestBehavior;
import group50.coupletones.auth.user.behavior.ProfileBehavior;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.properties.ConcreteProperties;
import group50.coupletones.util.properties.Properties;
import rx.Observable;

import java.util.List;

/**
 * Represents a Partner that a User.
 * Delegates all methods to ConcreteUser, but prevents modification
 * to fields.
 *
 * @author Brandon Chi
 * @since 5/5/2016
 */
public class ConcretePartner implements Partner {
  // Composed behavior
  private final Properties properties;
  private final ProfileBehavior profile;
  private final PartnerRequestBehavior request;

  private String partnerId;

  /**
   * @param sync The object handling synchronizing partner data
   */
  public ConcretePartner(Sync sync) {
    properties = new ConcreteProperties();
    profile = new ProfileBehavior(properties);
    request = new PartnerRequestBehavior(properties);
    properties.property("partnerId").bind(this);
    sync.watchAll(properties);
  }

  @Override
  public String getPartnerId() {
    return partnerId;
  }

  @Override
  public String getId() {
    return profile.getId();
  }

  @Override
  public String getName() {
    return profile.getName();
  }

  @Override
  public String getEmail() {
    return profile.getEmail();
  }

  @Override
  public List<FavoriteLocation> getFavoriteLocations() {
    return profile.getFavoriteLocations();
  }

  @Override
  public List<VisitedLocationEvent> getVisitedLocations() { return profile.getVisitedLocations(); }

  @Override
  public void requestPartner(User requester) {
    request.requestPartner(requester);
  }

  @Override
  public Properties getProperties() {
    return properties;
  }

  @Override
  public Observable<User> load() {
    return Observable.zip(properties.allObservables(), args -> this);
  }
}

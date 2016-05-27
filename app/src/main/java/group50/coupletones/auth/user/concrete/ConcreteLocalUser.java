package group50.coupletones.auth.user.concrete;

import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.behavior.PartnerBehavior;
import group50.coupletones.auth.user.behavior.PartnerRequestBehavior;
import group50.coupletones.auth.user.behavior.ProfileBehavior;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.sync.Sync;
import rx.Observable;

import java.util.List;

/**
 * Represents a concrete implementation of LocalUser.
 * Behavior is composed via Strategy Pattern.
 * @author Henry Mao
 */
public class ConcreteLocalUser implements LocalUser {
  // Composed behavior
  private final ProfileBehavior profileBehavior;

  private final PartnerRequestBehavior requestBehavior;

  private final PartnerBehavior partnerBehavior;

  public ConcreteLocalUser(Sync sync) {
    profileBehavior = new ProfileBehavior(sync);
    requestBehavior = new PartnerRequestBehavior(sync);
    partnerBehavior = new PartnerBehavior(sync, requestBehavior);
  }

  @Override
  public User getPartner() {
    return partnerBehavior.getPartner();
  }

  @Override
  public void setPartner(String partnerId) {
    partnerBehavior.setPartner(partnerId);
  }

  @Override
  public String getId() {
    return profileBehavior.getId();
  }

  @Override
  public String getName() {
    return profileBehavior.getName();
  }

  @Override
  public String getEmail() {
    return profileBehavior.getEmail();
  }

  @Override
  public List<FavoriteLocation> getFavoriteLocations() {
    return profileBehavior.getFavoriteLocations();
  }

  @Override
  public List<VisitedLocationEvent> getVisitedLocations() {
    return profileBehavior.getVisitedLocations();
  }

  @Override
  public void addFavoriteLocation(FavoriteLocation location) {
    profileBehavior.addFavoriteLocation(location);
  }

  @Override
  public void addVisitedLocation(VisitedLocationEvent visitedLocation) {
    profileBehavior.addVisitedLocation(visitedLocation);
  }

  @Override
  public void removeFavoriteLocation(FavoriteLocation location) {
    profileBehavior.removeFavoriteLocation(location);
  }

  @Override
  public void requestPartner(User requester) {
    requestBehavior.requestPartner(requester);
  }

  @Override
  public void handlePartnerRequest(String partnerId, boolean accept) {
    partnerBehavior.handlePartnerRequest(partnerId, accept);
  }

  @Override
  public Observable<User> getPartnerObservable() {
    return partnerBehavior.getPartnerObservable();
  }

  @Override
  public <T> Observable<T> getObservable(String name) {
    return Observable.merge(
      profileBehavior.getObservable(name),
      requestBehavior.getObservable(name),
      partnerBehavior.getObservable(name)
    );
  }

}

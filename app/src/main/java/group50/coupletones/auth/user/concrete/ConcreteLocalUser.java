package group50.coupletones.auth.user.concrete;

import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.behavior.PartnerBehavior;
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
 * Represents a concrete implementation of LocalUser.
 * Behavior is composed via Strategy Pattern.
 *
 * @author Henry Mao
 */
public class ConcreteLocalUser implements LocalUser {
  // Composed behavior
  private final Properties properties;

  private final ProfileBehavior profile;

  private final PartnerRequestBehavior requestBehavior;

  private final PartnerBehavior partnerBehavior;

  public ConcreteLocalUser(Sync sync) {
    properties = new ConcreteProperties();
    profile = new ProfileBehavior(properties, sync);
    requestBehavior = new PartnerRequestBehavior(properties, sync);
    partnerBehavior = new PartnerBehavior(properties, this, sync, requestBehavior);
    sync.watchAll(properties);
  }

  @Override
  public Observable<Partner> getPartner() {
    return partnerBehavior.getPartner();
  }

  @Override
  public void setPartner(String partnerId) {
    partnerBehavior.setPartner(partnerId);
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
  public boolean getGlobalNotificationsSetting() {
    return profile.getGlobalNotificationsSetting();
  }

  @Override
  public boolean getTonesSetting() {
    return profile.getTonesSetting();
  }

  @Override
  public boolean getVibrationSetting() {
    return profile.getVibrationSetting();
  }

  @Override
  public boolean setGlobalNotificationsSetting(boolean setting) {
    return profile.setGlobalNotificationsSetting(setting);
  }

  @Override
  public boolean setTonesSetting(boolean setting) {
    return profile.setTonesSetting(setting);
  }

  @Override
  public boolean setVibrationSetting(boolean setting) {
    return profile.setVibrationSetting(setting);
  }

  @Override
  public List<VisitedLocationEvent> getVisitedLocations() {
    return profile.getVisitedLocations();
  }

  @Override
  public void addFavoriteLocation(FavoriteLocation location) {
    profile.addFavoriteLocation(location);
  }

  @Override
  public void addVisitedLocation(VisitedLocationEvent visitedLocation) {
    profile.addVisitedLocation(visitedLocation);
  }

  @Override
  public void removeFavoriteLocation(FavoriteLocation location) {
    profile.removeFavoriteLocation(location);
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
  public Properties getProperties() {
    return properties;
  }

  @Override
  public Observable<User> load() {
    return Observable.zip(properties.allObservables(), args -> this);
  }

  @Override
  public String getFcmToken() {
    return profile.getFcmToken();
  }

  @Override
  public void setFcmToken(String id) {
    profile.setFcmToken(id);
  }

  @Override
  public void setFavoriteLocation(int index, FavoriteLocation location) {
    profile.setFavoriteLocation(index, location);
  }
}

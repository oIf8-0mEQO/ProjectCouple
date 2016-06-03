package group50.coupletones.auth.user.concrete;

import android.util.Log;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.User;
import group50.coupletones.auth.user.behavior.PartnerRequestBehavior;
import group50.coupletones.auth.user.behavior.ProfileBehavior;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.properties.ConcreteProperties;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.Property;
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
public class ConcretePartner implements Partner, Taggable {
  // Composed behavior
  private final Properties properties;
  private final ProfileBehavior profile;
  private final PartnerRequestBehavior request;

  private String partnerId;

  private Sync sync;

  /**
   * @param sync The object handling synchronizing partner data
   */
  public ConcretePartner(Sync sync) {
    properties = new ConcreteProperties();
    profile = new ProfileBehavior(properties, sync, this);
    request = new PartnerRequestBehavior(properties, sync);
    properties.property("partnerId").bind(this);
    sync.watchAll(properties);
    this.sync = sync;
  }

  @Override
  public String getPartnerId() {
    return partnerId;
  }

  @Override
  public void setPartnerId(String partnerId) {
    // Set partner id to this partner.
    this.partnerId = partnerId;
    Property<Object> prop = properties.property("partnerId");
    sync.update(prop);
    prop.update();
  }


  @Override
  public String getId() {
    return profile.getId();
  }

  @Override
  public String getFcmToken() {
    return profile.getFcmToken();
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
  public List<VisitedLocationEvent> getVisitedLocations() {
    return profile.getVisitedLocations();
  }

  @Override
  public void requestPartner(User requester) {
    request.requestPartner(requester);
  }

  @Override
  public void setVibeTone(int locationId, int vibeToneId) {
    List<FavoriteLocation> favoriteLocations = getFavoriteLocations();
    if (locationId < favoriteLocations.size()) {
      FavoriteLocation favoriteLocation = favoriteLocations.get(locationId);
      favoriteLocation.setVibeToneId(vibeToneId);
      profile.setFavoriteLocation(locationId, favoriteLocation);
    } else {
      Log.e(getTag(), "Vibe tone assigning to invalid location. Partner might have changed their locations!");
    }
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

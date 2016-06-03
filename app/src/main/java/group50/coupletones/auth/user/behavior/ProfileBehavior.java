/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth.user.behavior;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.GenericTypeIndicator;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.FcmMessage;
import group50.coupletones.network.fcm.message.MessageType;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.util.TimeUtility;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.PropertiesProvider;
import group50.coupletones.util.properties.Property;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the behavior of user's profile. Strategy pattern.
 */
public class ProfileBehavior implements PropertiesProvider {
  private static final String NOTIFY_TITLE = "%1$s visited %2$s";
  private static final String NOTIFY_ICON = "icon";

  /**
   * Object responsible for syncing the object with database
   */
  private final Properties properties;
  private final Sync sync;

  @Inject
  @Exclude
  public TimeUtility timeUtility;

  @Inject
  @Exclude
  public FormatUtility formatUtility;

  @Inject
  public NetworkManager network;
  /**
   * User object
   */
  User user;
  /**
   * Google user id
   */
  private String id;
  /**
   * Booleans that handle settings toggling
   */
  private boolean globalNotificationsAreOn = true;
  private boolean tonesAreOn = true;
  private boolean vibrationIsOn = true;
  /**
   * ID for FCM
   */
  private String fcmToken;
  /**
   * Name of the user
   */
  private String name;
  /**
   * Email of the user
   */
  private String email;
  /**
   * The user's list of favorite location.
   */
  private List<FavoriteLocation> favoriteLocations = new LinkedList<>();
  /**
   * The user's list of visited locations.
   */
  private List<VisitedLocationEvent> visitedLocations = new LinkedList<>();

  /**
   * Creates a ConcreteUser
   */
  public ProfileBehavior(Properties properties, Sync sync, User user) {
    CoupleTones.global().inject(this);

    this.user = user;
    this.properties = properties
      .property("id").bind(this)
      .property("fcmToken").bind(this)
      .property("name").bind(this)
      .property("email").bind(this)
      .property("globalNotificationsAreOn").bind(this)
      .property("tonesAreOn").bind(this)
      .property("vibrationIsOn").bind(this)
      .property("favoriteLocations")
      .mark(new GenericTypeIndicator<List<FavoriteLocation>>() {
      })
      .bind(this)
      .property("visitedLocations")
      .mark(new GenericTypeIndicator<List<VisitedLocationEvent>>() {
      })
      .bind(this);

    this.sync = sync;
  }

  /**
   * @return Global notifications setting
   */
  public boolean getGlobalNotificationsSetting() {
    return globalNotificationsAreOn;
  }

  /**
   * @return Tones setting
   */
  public boolean getTonesSetting() {
    return tonesAreOn;
  }

  /**
   * @return Vibration setting
   */
  public boolean getVibrationSetting() {
    return vibrationIsOn;
  }

  /**
   * This function turns on/off the global notifications setting
   *
   * @return globalNotificationsAreOn true if notifications are on, false if turned off
   */
  public Boolean setGlobalNotificationsSetting(boolean setting) {
    globalNotificationsAreOn = setting;

    Property<Object> prop = properties.property("globalNotificationsAreOn");
    sync.update(prop);
    prop.update();

    return globalNotificationsAreOn;
  }

  /**
   * This function turns on/off tones
   *
   * @return tonesAreOn true if tones are on, false if turned off
   */
  public Boolean setTonesSetting(boolean setting) {
    tonesAreOn = setting;

    Property<Object> prop = properties.property("tonesAreOn");
    sync.update(prop);
    prop.update();

    return tonesAreOn;
  }

  /**
   * This function turns on/off vibration
   *
   * @return vibrationIsOn true if vibration is on, false if turned off
   */
  public Boolean setVibrationSetting(boolean setting) {
    vibrationIsOn = setting;

    Property<Object> prop = properties.property("vibrationIsOn");
    sync.update(prop);
    prop.update();

    return vibrationIsOn;
  }

  /**
   * @return The id of the user
   */
  public String getId() {
    return id;
  }

  /**
   * @return The name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * @return The email of the user
   */
  public String getEmail() {
    return email;
  }

  public String getFcmToken() {
    return fcmToken;
  }

  public void setFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
    sync.update(properties.property("fcmToken"));
    properties.property("fcmToken").update();
  }

  /**
   * @return The list of favorite locations of the user
   */
  public List<FavoriteLocation> getFavoriteLocations() {
    return favoriteLocations != null ? Collections.unmodifiableList(favoriteLocations) : Collections.emptyList();
  }

  /**
   * @return The list of visited locations of the user
   */
  public List<VisitedLocationEvent> getVisitedLocations() {

    if (visitedLocations != null) {
      Iterator<VisitedLocationEvent> it = visitedLocations.iterator();
      boolean hasChanged = false;

      while (it.hasNext()) {
        VisitedLocationEvent currEvent = it.next();
        if (timeUtility.isFromPreviousDay(currEvent)) {
          it.remove();
          hasChanged = true;
        }
      }

      if (hasChanged) {
        Property<Object> prop = properties.property("visitedLocations");
        sync.update(prop);
        prop.update();
      }

      return Collections.unmodifiableList(visitedLocations);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * Adds a favorite location
   *
   * @param location The location to add
   */
  public void addFavoriteLocation(FavoriteLocation location) {
    if (favoriteLocations == null) {
      favoriteLocations = new LinkedList<>();
    }
    favoriteLocations.add(location);

    Property<Object> prop = properties.property("favoriteLocations");
    sync.update(prop);
    prop.update();
  }

  /**
   * Adds a favorite location
   *
   * @param location The location to add
   */
  public void setFavoriteLocation(int index, FavoriteLocation location) {
    if (favoriteLocations != null) {
      if (index < favoriteLocations.size()) {
        favoriteLocations.set(index, location);
        Property<Object> prop = properties.property("favoriteLocations");
        sync.update(prop);
        prop.update();
      }
    }
  }

  /**
   * Adds a visited location
   *
   * @param visitedLocation The visited location to add.
   */
  public void addVisitedLocation(VisitedLocationEvent visitedLocation) {
    if (visitedLocations == null) {
      visitedLocations = new LinkedList<>();
    }
    visitedLocations.add(0, visitedLocation);

    Property<Object> prop = properties.property("visitedLocations");
    prop.set(this.visitedLocations);
    sync.update(prop);
    prop.update();

    // Send notification to partner about location visit
    if (user instanceof LocalUser) {
      ((LocalUser) user).getPartner()
        .filter(partner -> partner != null)
        .subscribe(partner -> {
          network
            .getOutgoingStream()
            .onNext(
              new FcmMessage(MessageType.LOCATION_NOTIFICATION.value)
                .setTitle(String.format(NOTIFY_TITLE, user.getName(), visitedLocation.getName()))
                .setBody(formatUtility.formatDate(visitedLocation.getTimeVisited()))
                .setIcon(NOTIFY_ICON)
                .setTo(partner.getFcmToken())
            );
        });
    }
  }

  /**
   * Removes a favorite location
   *
   * @param location The location to remove
   */
  public void removeFavoriteLocation(FavoriteLocation location) {
    if (favoriteLocations != null) {
      favoriteLocations.remove(location);

      Property<Object> prop = properties.property("favoriteLocations");
      prop.set(this.favoriteLocations);
      sync.update(prop);
      prop.update();
    }
  }

  public void updateCooldownOfFavorite(FavoriteLocation location) {
    location.setTimeLastVisited(System.currentTimeMillis());

    Property<Object> prop = properties.property("favoriteLocations");
    sync.update(prop);
    prop.update();
  }

  @Override
  public Properties getProperties() {
    return properties;
  }
}

/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones;

import android.app.Application;
import android.location.Geocoder;
import com.google.firebase.database.FirebaseDatabase;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityNetworkHandler;
import group50.coupletones.di.DaggerGlobalComponent;
import group50.coupletones.di.DaggerInstanceComponent;
import group50.coupletones.di.GlobalComponent;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.ProximityModule;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.receiver.ErrorReceiver;
import group50.coupletones.network.receiver.LocationNotificationReceiver;

/**
 * A singleton object that holds global data.
 * Represents the main Android application.
 * Lifecycle of instance persists as long as the app is running.
 */
public class CoupleTones extends Application {

  /**
   * The singleton dependency injector
   */
  private static GlobalComponent component;

  /**
   * The instance dependency injector builder
   */
  private static DaggerInstanceComponent.Builder instanceComponentBuilder;

  /**
   * The local user of the app
   */
  private LocalUser localUser;

  /**
   * @return The main dependency injection global
   */
  public static GlobalComponent global() {
    return component;
  }

  /**
   * Should ONLY be set for unit testing
   *
   * @param component The global to set
   */
  public static void setGlobal(GlobalComponent component) {
    CoupleTones.component = component;
  }

  public static DaggerInstanceComponent.Builder instanceComponentBuilder() {
    return instanceComponentBuilder;
  }

  public static void setInstanceComponentBuilder(DaggerInstanceComponent.Builder instanceComponentBuilder) {
    CoupleTones.instanceComponentBuilder = instanceComponentBuilder;
  }

  /**
   * @return The local user. If the user is not logged in, null is returned.
   */
  public LocalUser getLocalUser() {
    return localUser;
  }

  /**
   * Sets the local user of the app. This method should only be
   * during login/logout events.
   *
   * @param localUser The local user object
   */
  public void setLocalUser(LocalUser localUser) {
    this.localUser = localUser;
  }

  /**
   * @return True if and only if the local user is logged into the app.
   */
  public boolean isLoggedIn() {
    return localUser != null;
  }


  @Override
  public void onCreate() {
    super.onCreate();

    component = DaggerGlobalComponent
      .builder()
      .applicationModule(new ApplicationModule(this))
      .proximityModule(new ProximityModule(new Geocoder(getApplicationContext())))
      .build();

    setInstanceComponentBuilder(
      DaggerInstanceComponent
        .builder()
    );

    // Enable Firebase persistence
    FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    // Register network
    NetworkManager network = global().network();
    network.register(this);
    network.register(new LocationNotificationReceiver(this, this));
    network.register(MessageType.RECEIVE_PARTNER_ERROR.value, new ErrorReceiver(this));
    network.register(MessageType.RECEIVE_MAP_REJECT.value, new ErrorReceiver(this));

    // Register location observer
    ProximityManager proximity = global().proximity();
    proximity.register(new ProximityNetworkHandler(this, network));
  }
}

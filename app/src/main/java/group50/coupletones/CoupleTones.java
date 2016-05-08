/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones;

import android.app.Application;
import android.content.Intent;
import android.location.Geocoder;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityNetworkHandler;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityService;
import group50.coupletones.di.AppComponent;
import group50.coupletones.di.DaggerAppComponent;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.ProximityModule;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.receiver.ErrorReceiver;
import group50.coupletones.network.receiver.PartnerRequestReceiver;
import group50.coupletones.network.receiver.PartnerResponseReceiver;

/**
 * A singleton object that holds global data.
 * Represents the main Android application.
 * Lifecycle of instance persists as long as the app is running.
 */
public class CoupleTones extends Application {

  /**
   * The main dependency injection component
   */
  private static AppComponent component;
  /**
   * The local user of the app
   */
  private LocalUser localUser;

  /**
   * @return The main dependency injection component
   */
  public static AppComponent component() {
    return component;
  }

  /**
   * Should ONLY be set for unit testing
   *
   * @param component The component to set
   */
  public static void setComponent(AppComponent component) {
    CoupleTones.component = component;
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

    component = DaggerAppComponent
      .builder()
      .applicationModule(new ApplicationModule(this))
      .proximityModule(new ProximityModule(new Geocoder(getApplicationContext())))
      .build();

    // Register network
    NetworkManager network = component().network();
    network.register(this);
    network.register(new PartnerRequestReceiver(this));
    network.register(new PartnerResponseReceiver(this, this));
    network.register(MessageType.RECEIVE_PARTNER_ERROR.value, new ErrorReceiver(this));
    network.register(MessageType.RECEIVE_MAP_REJECT.value, new ErrorReceiver(this));

    // Register location observer
    ProximityManager proximity = component().proximity();
    proximity.register(new ProximityNetworkHandler(network));

    // Start ProximityService
    startService(new Intent(this, ProximityService.class));
  }
}

package group50.coupletones.di;

import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.user.UserFactory;
import group50.coupletones.auth.user.behavior.PartnerBehavior;
import group50.coupletones.auth.user.behavior.ProfileBehavior;
import group50.coupletones.controller.AddPartnerActivity;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.PartnerResponseActivity;
import group50.coupletones.controller.tab.favoritelocations.EditLocationActivity;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsListAdapter;
import group50.coupletones.controller.tab.favoritelocations.map.*;
import group50.coupletones.controller.tab.favoritelocations.map.location.AddressProvider;
import group50.coupletones.controller.tab.favoritelocations.map.location.ConcreteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.controller.tab.partnerslocations.ListAdapterPartnerVisited;
import group50.coupletones.controller.tab.settings.SettingsFragment;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.NetworkModule;
import group50.coupletones.di.module.ProximityModule;
import group50.coupletones.di.module.UserFactoryModule;
import group50.coupletones.network.fcm.InstanceIDService;
import group50.coupletones.network.fcm.MessagingService;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.gcm.GcmIntentService;
import group50.coupletones.util.sound.VibeTone;
import group50.coupletones.util.TimeUtility;

import javax.inject.Singleton;

/**
 * The dependency injection global for the entire app.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Singleton
@Component(
  modules = {
    ApplicationModule.class,
    NetworkModule.class,
    ProximityModule.class,
    UserFactoryModule.class
  }
)
public interface GlobalComponent {

  CoupleTones app();

  NetworkManager network();

  ProximityManager proximity();

  AddressProvider geocoder();

  UserFactory userFactory();

  TimeUtility timeUtility();

  FormatUtility formatUtility();

  void inject(EditLocationActivity obj);

  void inject(LocationNotificationMediator obj);

  void inject(InstanceIDService obj);

  void inject(MessagingService obj);

  void inject(PartnerBehavior obj);

  void inject(FavoriteLocationsListAdapter obj);

  void inject(GoogleAuthenticator obj);

  void inject(LocationClickHandler obj);

  void inject(PartnerResponseActivity activity);

  void inject(VisitedLocationEvent fragment);

  void inject(FavoriteLocation fragment);

  void inject(FavoriteLocationsFragment fragment);

  void inject(ProximityService activity);

  void inject(AddPartnerActivity activity);

  void inject(MapFragment fragment);

  void inject(MainActivity activity);

  void inject(LoginActivity activity);

  void inject(SettingsFragment fragment);

  void inject(ConcreteLocation location);

  void inject(VibeTone vibeTone);

  void inject(ProfileBehavior behavior);

  void inject(ListAdapterPartnerVisited list);
}

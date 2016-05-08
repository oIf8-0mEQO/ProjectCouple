package group50.coupletones.di;

import com.google.android.gms.common.api.GoogleApiClient;
import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.AddPartnerActivity;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.PartnerResponseActivity;
import group50.coupletones.controller.tab.SettingsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.favoritelocations.map.*;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.di.module.NetworkModule;
import group50.coupletones.di.module.ProximityModule;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.gcm.GcmIntentService;

import javax.inject.Singleton;

/**
 * The dependency injection component for the entire app.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Singleton
@Component(
  modules = {
    ApplicationModule.class,
    AuthenticatorModule.class,
    NetworkModule.class,
    ProximityModule.class,
  }
)
public interface AppComponent {

  Authenticator<User, String> auth();

  CoupleTones app();

  NetworkManager network();

  ProximityManager proximity();

  AddressProvider geocoder();

  GoogleApiClient apiClient();

  void inject(PartnerResponseActivity activity);

  void inject(VisitedLocation fragment);

  void inject(FavoriteLocation fragment);

  void inject(FavoriteLocationsFragment fragment);

  void inject(ProximityService activity);

  void inject(AddPartnerActivity activity);

  void inject(MapFragment fragment);

  void inject(MainActivity activity);

  void inject(LoginActivity activity);

  void inject(SettingsFragment fragment);

  void inject(GcmIntentService receiver);
}

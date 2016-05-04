package group50.coupletones.di;

import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.User;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.di.module.NetworkModule;
import group50.coupletones.network.GcmMessageHandler;
import group50.coupletones.network.NetworkManager;

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
    NetworkModule.class
  }
)
public interface AppComponent {

  Authenticator<User, String> auth();

  CoupleTones app();

  NetworkManager network();

  void inject(LoginActivity activity);

  void inject(GcmMessageHandler receiver);
}

package group50.coupletones.di;

import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.User;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.di.module.ApplicationModule;
import group50.coupletones.di.module.AuthenticatorModule;

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
    AuthenticatorModule.class,
    ApplicationModule.class
  }
)
public interface MainComponent {

  Authenticator<User, String> auth();

  CoupleTones app();

  void inject(LoginActivity activity);
}

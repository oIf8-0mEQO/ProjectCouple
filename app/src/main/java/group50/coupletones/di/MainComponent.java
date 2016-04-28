package group50.coupletones.di;

import dagger.Component;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.User;
import group50.coupletones.controller.LoginActivity;

/**
 * Created by henry on 4/28/16.
 */
@Component(
  modules = {
    AuthenticatorModule.class
  }
)
public interface MainComponent {
  Authenticator<GoogleAuthenticator, User, String> auth();

  void inject(LoginActivity activity);
}

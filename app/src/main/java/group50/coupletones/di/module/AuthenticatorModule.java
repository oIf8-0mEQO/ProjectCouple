package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.user.User;

/**
 * Dependency injection module to provide the Authenticator object
 *
 * @author Henry Mao
 * @since 4/28/16.
 */
@Module
public class AuthenticatorModule {

  protected Authenticator<User, String> provideAuth(GoogleAuthenticator auth) {
    return auth;
  }

  @Provides
  public final Authenticator<User, String> provide(GoogleAuthenticator auth) {
    return provideAuth(auth);
  }
}

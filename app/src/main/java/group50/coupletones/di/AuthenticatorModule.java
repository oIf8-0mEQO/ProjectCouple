package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.User;

/**
 * Dependency injection module to provide the Authenticator object
 *
 * @author Henry Mao
 * @since 4/28/16.
 */
@Module
public class AuthenticatorModule {
  @Provides
  static Authenticator<GoogleAuthenticator, User, String> provide() {
    return new GoogleAuthenticator();
  }
}

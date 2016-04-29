package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.User;

import javax.inject.Singleton;

/**
 * Dependency injection module to provide the Authenticator object
 *
 * @author Henry Mao
 * @since 4/28/16.
 */
@Module
public class AuthenticatorModule {
  @Singleton
  @Provides
  static Authenticator<User, String> provide(GoogleAuthenticator auth) {
    return auth;
  }
}

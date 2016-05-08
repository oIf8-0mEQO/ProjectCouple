package group50.coupletones.di;

import dagger.Module;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.di.module.AuthenticatorModule;

import static org.mockito.Mockito.mock;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class MockAuthenticatorModule extends AuthenticatorModule {

  @Override
  protected Authenticator<User, String> provideAuth(GoogleAuthenticator auth) {
    return mock(Authenticator.class);
  }
}

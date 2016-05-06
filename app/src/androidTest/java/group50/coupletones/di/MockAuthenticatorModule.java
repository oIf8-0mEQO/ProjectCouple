package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class MockAuthenticatorModule {

  @Singleton
  @Provides
  static Authenticator<User, String> provide() {
    return mock(Authenticator.class);
  }
}

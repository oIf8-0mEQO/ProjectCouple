package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.fcm.NetworkManager;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 4/28/16
 */
@Module
public class MockNetworkModule {

  @Singleton
  @Provides
  static NetworkManager provide() {
    return mock(NetworkManager.class);
  }
}

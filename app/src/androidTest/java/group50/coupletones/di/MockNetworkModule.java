package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.NetworkManager;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class MockNetworkModule {

  @Singleton
  @Provides
  static NetworkManager provide() {
    return mock(NetworkManager.class);
  }
}

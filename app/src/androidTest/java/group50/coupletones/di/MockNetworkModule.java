package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.fcm.FcmManager;
import group50.coupletones.network.fcm.NetworkManager;

import javax.inject.Singleton;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

/**
 * Mock Authenticator Module
 * @author Henry Mao
 * @since 4/28/16
 */
@Module
public class MockNetworkModule {

  @Singleton
  @Provides
  static NetworkManager provide(FcmManager manager) {
    FcmManager spy = spy(manager);
    // Stub the sendMessage to prevent network interactions in tests
    doAnswer(ans -> null).when(spy).sendMessage(any());
    return spy;
  }
}

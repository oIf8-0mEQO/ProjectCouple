package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.user.UserFactory;
import group50.coupletones.network.sync.Sync;

import static org.mockito.Mockito.*;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
@Module
public class MockUserFactoryModule {
  @Provides
  protected UserFactory provide() {
    UserFactory spy = spy(new UserFactory());
    when(spy.buildSync()).thenReturn(mock(Sync.class));
    return spy;
  }
}

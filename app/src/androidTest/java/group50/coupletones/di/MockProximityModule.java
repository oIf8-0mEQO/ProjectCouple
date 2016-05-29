package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.location.AddressProvider;
import group50.coupletones.di.module.ProximityModule;
import rx.subjects.PublishSubject;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 4/28/16
 */
@Module
public class MockProximityModule extends ProximityModule {

  public MockProximityModule() {
    super(null);
  }

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  AddressProvider provideAddress() {
    return mock(AddressProvider.class);
  }

  /**
   * Expose the application to the graph.
   */
  @Singleton
  @Provides
  ProximityManager provideProximity() {
    ProximityManager mock = mock(ProximityManager.class);
    when(mock.getEnterSubject()).thenReturn(PublishSubject.create());
    when(mock.getExitSubject()).thenReturn(PublishSubject.create());
    return mock;
  }
}

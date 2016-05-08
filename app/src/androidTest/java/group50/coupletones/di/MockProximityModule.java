package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.controller.tab.favoritelocations.map.location.AddressProvider;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.di.module.ProximityModule;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

/**
 * Mock Authenticator Module
 *
 * @author Henry Mao
 * @since 28/4/2016
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
    return mock(ProximityManager.class);
  }
}

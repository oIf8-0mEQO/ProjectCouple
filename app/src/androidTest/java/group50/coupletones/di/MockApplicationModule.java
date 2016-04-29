package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.CoupleTones;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

/**
 * The mock dependency injection module that provides the main application singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class MockApplicationModule {

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  CoupleTones provide() {
    return mock(CoupleTones.class);
  }
}

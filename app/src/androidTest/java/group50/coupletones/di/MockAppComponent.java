package group50.coupletones.di;

import dagger.Component;

import javax.inject.Singleton;

/**
 * The dependency injection global for the entire app using mocks.
 *
 * @author Henry Mao
 * @since 4/28/16
 */
@Singleton
@Component(
  modules = {
    MockApplicationModule.class,
    MockNetworkModule.class,
    MockProximityModule.class,
    MockUserFactoryModule.class
  }
)
public interface MockAppComponent extends GlobalComponent {
}

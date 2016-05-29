package group50.coupletones.di;

import dagger.Component;
import group50.coupletones.di.module.AuthenticatorModule;
import group50.coupletones.di.module.ContextModule;

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
    ContextModule.class,
    AuthenticatorModule.class,
    MockUserFactoryModule.class
  }
)
public interface MockInstanceComponent extends InstanceComponent {
}

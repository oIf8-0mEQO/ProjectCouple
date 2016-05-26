package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.auth.user.UserFactory;

/**
 * Dependency injection module to provide the Authenticator object
 *
 * @author Henry Mao
 * @since 4/28/16.
 */
@Module
public class UserFactoryModule {

  @Provides
  protected UserFactory provide() {
    return new UserFactory();
  }
}

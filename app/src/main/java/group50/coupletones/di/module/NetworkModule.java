package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.gcm.GcmManager;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the network manager singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class NetworkModule {

  /**
   * Expose the application to the graph.
   */
  @Singleton
  @Provides
  NetworkManager provide(GcmManager manager) {
    return manager;
  }
}

package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.FcmManager;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the network manager singleton.
 *
 * @author Henry Mao
 * @since 4/28/16
 */
@Module
public class NetworkModule {

  /**
   * Expose the application to the graph.
   */
  @Singleton
  @Provides
  NetworkManager provide(FcmManager manager) {
    return manager;
  }
}

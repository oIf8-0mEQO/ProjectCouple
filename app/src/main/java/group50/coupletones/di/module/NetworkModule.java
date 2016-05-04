package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.network.NetworkManager;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the network manager singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class NetworkModule {
  private final NetworkManager manager;

  public NetworkModule(NetworkManager manager) {
    this.manager = manager;
  }

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  NetworkManager provide() {
    return manager;
  }
}

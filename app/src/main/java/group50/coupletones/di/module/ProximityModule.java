package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.controller.tab.favoritelocations.map.MapProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the location manager singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class ProximityModule {

  /**
   * Expose the application to the graph.
   */
  @Singleton
  @Provides
  ProximityManager provide(MapProximityManager manager) {
    return manager;
  }
}

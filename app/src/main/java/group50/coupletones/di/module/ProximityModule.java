package group50.coupletones.di.module;

import android.location.Address;
import android.location.Geocoder;
import dagger.Module;
import dagger.Provides;
import group50.coupletones.controller.tab.favoritelocations.map.location.AddressProvider;
import group50.coupletones.controller.tab.favoritelocations.map.MapProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;

import javax.inject.Singleton;
import java.util.List;

/**
 * The dependency injection module that provides the location manager singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class ProximityModule {
  public Geocoder geocoder;

  public ProximityModule(Geocoder geocoder) {
    this.geocoder = geocoder;
  }

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  AddressProvider provideAddress() {
    return (position) -> {
      try {
        List<Address> fromLocations = geocoder.getFromLocation(position.latitude, position.longitude, 1);

        if (fromLocations.size() > 0)
          return fromLocations.get(0);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    };
  }

  /**
   * Expose the application to the graph.
   */
  @Singleton
  @Provides
  ProximityManager provideProximity(MapProximityManager manager) {
    return manager;
  }
}

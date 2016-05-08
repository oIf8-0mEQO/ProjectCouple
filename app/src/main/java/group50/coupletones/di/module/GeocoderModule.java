package group50.coupletones.di.module;

import android.location.Geocoder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the geocoder singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
 */
@Module
public class GeocoderModule {
  public Geocoder geocoder;

  public GeocoderModule(Geocoder geocoder) {
    this.geocoder = geocoder;
  }

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  Geocoder provide() {
    return geocoder;
  }
}

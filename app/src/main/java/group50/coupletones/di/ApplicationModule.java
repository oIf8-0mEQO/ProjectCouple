package group50.coupletones.di;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.CoupleTones;

import javax.inject.Singleton;

/**
 * Created by henry on 4/28/16.
 */
@Module
public class ApplicationModule {
  private final CoupleTones application;

  public ApplicationModule(CoupleTones application) {
    this.application = application;
  }

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  CoupleTones application() {
    return application;
  }
}

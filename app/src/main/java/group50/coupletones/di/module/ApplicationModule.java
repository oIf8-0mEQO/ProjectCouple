package group50.coupletones.di.module;

import dagger.Module;
import dagger.Provides;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.util.TimeUtility;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the main application singleton.
 *
 * @author Henry Mao
 * @since 28/4/2016
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
  CoupleTones provide() {
    return application;
  }

  @Provides
  @Singleton
  TimeUtility provideTimeUtility() {
    return new TimeUtility();
  }

  @Provides
  @Singleton
  FormatUtility provideFormatUtility() {
    return new FormatUtility();
  }
}


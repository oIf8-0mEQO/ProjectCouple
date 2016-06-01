package group50.coupletones.di;

import com.google.android.gms.common.api.GoogleApiClient;
import dagger.Module;
import dagger.Provides;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.FormatUtility;
import group50.coupletones.util.TimeUtility;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * The mock dependency injection module that provides the main application singleton.
 *
 * @author Henry Mao
 * @since 4/28/16
 */
@Module
public class MockApplicationModule {

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  CoupleTones provide() {
    return mock(CoupleTones.class);
  }

  @Provides
  @Singleton
  GoogleApiClient provideApiClient() {
    return mock(GoogleApiClient.class);
  }

  @Provides
  @Singleton
  TimeUtility provideTimeUtility() {
    return spy(TimeUtility.class);
  }

  @Provides
  @Singleton
  FormatUtility provideFormatUtility() {
    return spy(FormatUtility.class);
  }
}

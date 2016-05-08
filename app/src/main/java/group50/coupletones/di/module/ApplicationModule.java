package group50.coupletones.di.module;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import dagger.Module;
import dagger.Provides;
import group50.coupletones.CoupleTones;

import javax.inject.Singleton;

/**
 * The dependency injection module that provides the main application singleton.
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

  /**
   * Expose the application to the graph.
   */
  @Provides
  @Singleton
  GoogleApiClient provideApiClient() {
    /*
     * Configure sign-in to request the user's ID, email address, and basic
     * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
     */
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail()
      .build();

      /*
       * Build a GoogleApiClient with access to the Google Sign-In API and the
       * options specified by gso.
      */
    return new GoogleApiClient.Builder(this.application)
      .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
      .addApi(LocationServices.API)
      .build();
  }
}

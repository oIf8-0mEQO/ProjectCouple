package group50.coupletones.auth;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import group50.coupletones.App;

/**
 * Handles Google Authentication
 *
 * @author Henry Mao
 */
public class GoogleAuthenticator implements GoogleApiClient.OnConnectionFailedListener {

  private static final String TAG = GoogleAuthenticator.class.getSimpleName();

  /**
   * The instance of the Google API client
   */
  private final GoogleApiClient googleApiClient;


  public GoogleAuthenticator(GoogleApiClient googleApiClient) {
    this.googleApiClient = googleApiClient;

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail()
      .build();

  }

  /**
   * Attempts to sign in the user automatically by using silent
   * sign in.
   *
   * @return
   */
  public GoogleAuthenticator autoSignIn() {
    OptionalPendingResult<GoogleSignInResult> pendingResult =
      Auth.GoogleSignInApi.silentSignIn(App.instance().getGoogleApiClient());

    //TODO: Refactor this
    if (pendingResult.isDone()) {
      Log.d(TAG, "Silent sign in handled");
      handleSignInResult(pendingResult.get());
    } else {
      Log.d(TAG, "Silent sign in being handled");
      // There's no immediate result ready, displays some progress indicator and waits for the
      // async callback.
      pendingResult.setResultCallback(result -> {
          Log.d(TAG, "Silent sign in handled: " + result.getStatus());
          handleSignInResult(result);
        }
      );
    }
    return this;
  }

  /**
   * Attempts to sign in the user.
   *
   * @return This instance
   */
  public GoogleAuthenticator signIn(Context context) {


    return this;
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    //TODO: Improve error handling
    Log.e(TAG, "Failed to login: " + connectionResult.getErrorMessage());
  }
}

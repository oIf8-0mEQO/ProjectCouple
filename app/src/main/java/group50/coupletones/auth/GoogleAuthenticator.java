package group50.coupletones.auth;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.function.Function;

import javax.inject.Inject;

/**
 * Handles Google Authentication
 *
 * @author Henry Mao
 */
public class GoogleAuthenticator implements
  Authenticator<User, String>,
  GoogleApiClient.OnConnectionFailedListener,
  Taggable {

  /**
   * The request code for sign in intent
   */
  private static final int RC_SIGN_IN = 9001;
  /**
   * Private instance to the CoupleTones app instance
   */
  private final CoupleTones app;
  /**
   * The activity initiating authentication
   */
  private FragmentActivity activity;
  /**
   * The callback function upon success
   */
  private Function<User, User> successCallback = x -> null;
  /**
   * The callback function upon failure
   */
  private Function<String, String> failCallback = x -> null;
  /**
   * The Google API client instance
   */
  private GoogleApiClient apiClient;

  @Inject
  public GoogleAuthenticator(CoupleTones app) {
    this.app = app;
  }

  /**
   * Binds the authenticator with a given activity.
   * Required to get the authenticator working
   *
   * @param activity The activity that is attempting to initiate sign in
   */
  @Override
  public GoogleAuthenticator bind(FragmentActivity activity) {
    this.activity = activity;

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
    apiClient = new GoogleApiClient.Builder(this.activity)
      .enableAutoManage(this.activity, this)
      .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
      .build();

    return this;
  }

  /**
   * Attempts to sign in the user automatically by using silent
   * sign in.
   *
   * @return This instance
   */
  @Override
  public GoogleAuthenticator autoSignIn() {
    OptionalPendingResult<GoogleSignInResult> pendingResult =
      Auth.GoogleSignInApi.silentSignIn(apiClient);

    //TODO: Refactor this
    if (pendingResult.isDone()) {
      Log.d(getTag(), "Silent sign in handled");
      handleSignInResult(pendingResult.get());
    } else {
      Log.d(getTag(), "Silent sign in being handled...");
      // There's no immediate result ready, displays some progress indicator and waits for the
      // async callback.
      pendingResult.setResultCallback(result -> {
        Log.d(getTag(), "Silent sign in handled: " + result.getStatus());
          handleSignInResult(result);
        }
      );
    }
    return this;
  }

  /**
   * Attempts to sign in the user.
   * Opens an Android Intent and asks Google to sign in.
   *
   * @return This instance
   */
  @Override
  public GoogleAuthenticator signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
    activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    return this;
  }

  /**
   * Method that handles the intent result callback
   *
   * @param requestCode The request code of the intent
   * @param resultCode  The result code of the intent
   * @param data        The data of the intent
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);
    }
  }

  /**
   * Handles the sign in request. Called after the sign in request is complete.
   *
   * @param result The GoogleSignInResult
   */
  private void handleSignInResult(GoogleSignInResult result) {
    Log.d(getTag(), "handleSignInResult: " + result.isSuccess());
    if (result.isSuccess()) {
      // Signed in successfully, store authenticated user
      GoogleUser localUser = new GoogleUser(result.getSignInAccount());
      app.setLocalUser(localUser);
      successCallback.apply(localUser);
    } else {
      // Signed out, show unauthenticated UI.
      app.setLocalUser(null);
      //TODO: Is returning null appropriate?
      successCallback.apply(null);
    }
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    //TODO: Improve error handling
    Log.e(getTag(), "Failed to login: " + connectionResult.getErrorMessage());
    failCallback.apply(connectionResult.getErrorMessage());
  }

  /**
   * @param callback The callback function upon successful sign in
   * @return This instance
   */
  @Override
  public GoogleAuthenticator onSuccess(Function<User, User> callback) {
    successCallback = callback;
    return this;
  }

  /**
   * @param callback The callback function upon fail sign in
   * @return This instance
   */
  @Override
  public GoogleAuthenticator onFail(Function<String, String> callback) {
    failCallback = callback;
    return this;
  }
}
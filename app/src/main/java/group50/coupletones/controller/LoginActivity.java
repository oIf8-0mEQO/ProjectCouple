/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import group50.coupletones.App;
import group50.coupletones.R;
import group50.coupletones.auth.GoogleUser;

/**
 * The LoginActivity controls the login page of the app.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    /**
     * The tag used for this activity
     */
    private static final String TAG = "LoginActivity";

    /**
     * The request code for sign in intent
     */
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        App.instance().setGoogleApiClient(
                new GoogleApiClient.Builder(this)
                        .enableAutoManage(this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build()
        );

        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(App.instance().getGoogleApiClient());

        //TODO: Refactor this
        if (pendingResult.isDone()) {
            Log.d(TAG, "Silent sign in handled");
            handleSignInResult(pendingResult.get());
        }

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // When the sign in button is clicked
            case R.id.sign_in_button:
                onSignIn();
                break;
        }
    }

    /**
     * Called when the sign in button is clicked.
     * <p>
     * Opens an Android Intent and asks Google to sign in.
     */
    private void onSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(App.instance().getGoogleApiClient());
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO: Improve err message
        Log.e(TAG, "Failed to connect");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /**
     * Handles the sign in request
     *
     * @param result The GoogleSignInResult
     */
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult: " + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, store authenticated user
            App.instance().setLocalUser(new GoogleUser(result.getSignInAccount()));
        } else {
            // Signed out, show unauthenticated UI.
            App.instance().setLocalUser(null);
        }
    }
}

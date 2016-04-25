package group50.coupletones.auth;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Handles Google Authentication
 *
 * @author Henry Mao
 */
public class GoogleAuthenticator implements GoogleApiClient.OnConnectionFailedListener {

    /**
     * The instance of the Google API client
     */
    private final GoogleApiClient googleApiClient;


    public GoogleAuthenticator(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

package group50.coupletones.network.fcm;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import group50.coupletones.util.Taggable;

/**
 * FCM ID refresher
 * @author Henry Mao
 */
public class InstanceIDService extends FirebaseInstanceIdService implements Taggable {

  @Override
  public void onTokenRefresh() {
    // Get updated InstanceID token.
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(getTag(), "Token refresh: " + refreshedToken);

    //TODO: Update user instance?
  }
}

package group50.coupletones.network.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * FCM ID refresher
 * @author Henry Mao
 */
public class InstanceIDService extends FirebaseInstanceIdService implements Taggable {

  @Inject
  public CoupleTones app;

  public InstanceIDService() {
    CoupleTones.global().inject(this);
  }

  @Override
  public void onTokenRefresh() {
    // Get updated InstanceID token.
    String token = FirebaseInstanceId.getInstance().getToken();

    // Update the token on the user
    if (app.getLocalUser() != null) {
      app.getLocalUser().setFcmToken(token);
    }
  }
}

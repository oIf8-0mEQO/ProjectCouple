
package group50.coupletones.network.gcm;

/**
 * @author Henry Mao
 * @since 22-04-2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import group50.coupletones.CoupleTones;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

public class GcmMessageHandler extends IntentService implements Taggable {

  @Inject
  public NetworkManager network;

  public GcmMessageHandler() {
    super("GcmMessageHandler");
  }

  @Override
  public void onCreate() {
    super.onCreate();

    // Dependency Injection
    CoupleTones.component().inject(this);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Bundle extras = intent.getExtras();

    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
    String messageType = gcm.getMessageType(intent);

    Log.d(getTag(), "GCM received: " + extras.toString() + " with message type: " + messageType);

    if (!extras.isEmpty() && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
      ((GcmManager) network).handleReceive(extras);
    }

    GcmReceiver.completeWakefulIntent(intent);
  }
}
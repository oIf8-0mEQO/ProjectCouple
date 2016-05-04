
package group50.coupletones.network;

/**
 * Created by Agneev on 22-04-2016.
 */

import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {

  String msg;
  private Handler handler;

  public GcmMessageHandler() {
    super("GcmMessageHandler");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    handler = new Handler();
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Bundle extras = intent.getExtras();

    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

    String messageType = gcm.getMessageType(intent);

    msg = extras.getString("message");
    showToast();

    Log.i("GCM", "Received: (" + messageType + ") " + extras.getString("message"));

    GcmReceiver.completeWakefulIntent(intent);
  }

  public void showToast() {
    handler.post(new Runnable() {

      @Override
      public void run() {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
      }
    });
  }
}
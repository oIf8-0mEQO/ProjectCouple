
package group50.coupletones.network;

/**
 * @author Henry Mao
 * @since 22-04-2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.gcm.GcmReceiver;
import group50.coupletones.CoupleTones;

import javax.inject.Inject;

public class GcmMessageHandler extends IntentService {

  @Inject
  private NetworkManager network;

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

    //TODO: Call NetworkManager.handleReceive(extras);

    GcmReceiver.completeWakefulIntent(intent);
  }
}
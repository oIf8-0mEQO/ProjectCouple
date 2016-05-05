
package group50.coupletones.network.gcm;

/**
 * @author Henry Mao
 * @since 22-04-2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmReceiver;

import group50.coupletones.CoupleTones;
import group50.coupletones.network.NetworkManager;

import javax.inject.Inject;

public class GcmMessageHandler extends IntentService {

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

    ((GcmManager) network).handleReceive(extras);

    GcmReceiver.completeWakefulIntent(intent);
  }
}
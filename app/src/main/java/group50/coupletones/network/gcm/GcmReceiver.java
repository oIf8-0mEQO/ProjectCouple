package group50.coupletones.network.gcm;

/**
 * Receives a GCM push data and initializes a GcmIntentService.
 *
 * @author Henry Mao
 * @since 4/22/16
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * GCM Receiver class
 */
public class GcmReceiver extends WakefulBroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
    startWakefulService(context, (intent.setComponent(comp)));
    setResultCode(Activity.RESULT_OK);
  }

}
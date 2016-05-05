package group50.coupletones.network.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.util.Identifiable;

/**
 * Handles receiving partner request.
 *
 * @author Henry Mao
 * @since 5/4/16
 */
public class PartnerRequestReceiver implements MessageReceiver, Identifiable {

  private final Context context;

  public PartnerRequestReceiver(Context context) {
    this.context = context;
  }

  @Override
  public void onReceive(Message message) {
    // The partner making the request
    String partnerEmail = message.getString("partner");
    //TODO: Use values/strings
    String title = "Partner Request";
    String msg = partnerEmail + " wants to partner with you!";

    Intent intent = new Intent(context, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(
      context,
      0 /* Request code */,
      intent,
      PendingIntent.FLAG_ONE_SHOT
    );

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.drawable.faves_icon)
      .setContentTitle(title)
      .setContentText(msg)
      .setAutoCancel(true)
      .setSound(defaultSoundUri)
      .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

  @Override
  public String getId() {
    return "partner-request";
  }
}

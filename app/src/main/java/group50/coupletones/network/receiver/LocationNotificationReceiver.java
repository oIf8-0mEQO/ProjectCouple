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
 * @author Sharmaine Manalo
 * @since 5/5/16
 */
public class LocationNotificationReceiver implements MessageReceiver, Identifiable {

  private final Context context;

  public LocationNotificationReceiver(Context context) {
    this.context = context;
  }

  @Override
  public void onReceive(Message message) {
    String locationName = message.getString("location");
    String locationTime = message.getString("time");
    // TODO: Make strings
    String title = "Partner visited " + locationName;
    String msg = locationTime;

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
        .setSmallIcon(R.drawable.target_icon)
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
    return "location-notification";
  }
}

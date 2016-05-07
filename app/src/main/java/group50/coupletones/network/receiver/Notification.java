package group50.coupletones.network.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import group50.coupletones.R;

/**
 * @author Henry
 * @since 5/6/16
 */
public class Notification {
  private final Context context;

  /**
   * The intent used when the notification is tapped on.
   */
  private Intent intent;

  /**
   * Title of notification
   */
  private String title;

  /**
   * The message of the notification
   */
  private String msg;

  public Notification(Context context) {
    this.context = context;
  }

  public Notification setIntent(Intent intent) {
    this.intent = intent;
    return this;
  }

  public Notification setTitle(String title) {
    this.title = title;
    return this;
  }

  public Notification setMsg(String msg) {
    this.msg = msg;
    return this;
  }

  public Notification show() {
    PendingIntent pendingIntent = PendingIntent.getActivity(
      context,
      0 /* Request code */,
      intent,
      PendingIntent.FLAG_ONE_SHOT
    );

    // Generate phone notification
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
    return this;
  }
}

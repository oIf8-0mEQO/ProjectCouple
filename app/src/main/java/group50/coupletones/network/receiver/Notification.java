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
 * A simple class that builds a notification
 *
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
   * The notification builder
   */
  private NotificationCompat.Builder builder;


  public Notification(Context context) {
    this.context = context;
    builder = new NotificationCompat.Builder(context);
  }

  public Notification setIntent(Intent intent) {
    this.intent = intent;
    return this;
  }

  public Notification setTitle(String title) {
    builder.setContentText(title);
    return this;
  }

  public Notification setMsg(String msg) {
    builder.setContentText(msg);
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
    builder
      .setSmallIcon(R.drawable.faves_icon)
      .setAutoCancel(true)
      .setSound(defaultSoundUri)
      .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, builder.build());
    return this;
  }
}

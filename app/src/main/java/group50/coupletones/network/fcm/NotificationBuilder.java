package group50.coupletones.network.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;

/**
 * A simple class that builds a notification
 *
 * @author Henry Mao
 * @since 5/6/16
 */
public class NotificationBuilder {
  private final Context context;

  /**
   * The intent used when the notification is tapped on.
   */
  private Intent intent;

  /**
   * The notification builder
   */
  private NotificationCompat.Builder builder;

  /**
   * Notification Constructor
   * @param context
   */
  public NotificationBuilder(Context context) {
    this.context = context;
    builder = new NotificationCompat.Builder(context);
    intent = new Intent(context, MainActivity.class);
  }

  /**
   * Sets the intent
   * @return - Notification user receives
   */
  public NotificationBuilder setIntent(Intent intent) {
    this.intent = intent;
    return this;
  }

  /**
   * Sets the title of notification
   * @param title - title of notification
   * @return - the Notification
   */
  public NotificationBuilder setTitle(String title) {
    builder.setContentTitle(title);
    return this;
  }

  /**
   * Sets the message of the notification
   * @param msg - Notification message
   * @return - the Notification
   */
  public NotificationBuilder setMsg(String msg) {
    builder.setContentText(msg);
    return this;
  }

  /**
   * Shows the notification
   * @return - the Notification
   */
  public NotificationBuilder show() {
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

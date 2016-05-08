package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.util.Identifiable;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */

/**
 * Location notification receiver class
 */
public class LocationNotificationReceiver implements MessageReceiver, Identifiable {

  private final Context context;

  public LocationNotificationReceiver(Context context) {
    this.context = context;
  }

  @Override
  public void onReceive(Message message) {
    String locationName = message.getString("name");
    String locationTime = message.getString("time");
    // TODO: Make strings
    String title = "Partner visited " + locationName;
    String msg = locationTime;

    Intent intent = new Intent(context, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    new Notification(context)
      .setIntent(intent)
      .setTitle(title)
      .setMsg(msg)
      .show();
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_MAP_NOTIFY;
  }
}

package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.util.Identifiable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    Notification notification = new Notification(context);
    //TODO: Use strings.xml
    notification.setTitle("Partner visited " + message.getString("name"));

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);
    try {
      Date parse = formatter.parse(message.getString("time"));
      notification.setMsg(parse.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

    Intent intent = new Intent(context, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    notification.setIntent(intent);
    notification.show();
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_MAP_NOTIFY.value;
  }
}

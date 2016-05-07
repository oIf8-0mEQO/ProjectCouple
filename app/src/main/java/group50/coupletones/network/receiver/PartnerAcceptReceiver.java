package group50.coupletones.network.receiver;

import android.content.Context;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.util.Identifiable;

/**
 * Handles the notification when a partner accepts a request
 *
 * @author Henry Mao
 * @since 5/4/16
 */
public class PartnerAcceptReceiver implements MessageReceiver, Identifiable {

  private final Context context;

  public PartnerAcceptReceiver(Context context) {
    this.context = context;
  }

  @Override
  public void onReceive(Message message) {
    // Phone notification
    String title = "Partner Request";
    String msg = message.getString("name") + " accepted your request!";

    new Notification(context)
      .setTitle(title)
      .setMsg(msg)
      .show();
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_ACCEPT.value;
  }
}

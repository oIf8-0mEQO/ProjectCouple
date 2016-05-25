package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.util.Identifiable;

/**
 * Handles the notification when a partner accepts or reject request.
 *
 * @author Henry Mao
 * @since 5/4/16
 */
public class PartnerResponseReceiver implements MessageReceiver, Identifiable {

  private final Context context;
  public CoupleTones app;

  public PartnerResponseReceiver(Context context, CoupleTones app) {
    this.context = context;
    this.app = app;
  }

  /**
   * onReceive of Partner Response
   * @param message The incoming message object that contains data from the server
   */
  @Override
  public void onReceive(Message message) {
    String title = context.getString(R.string.partner_request_header);
    boolean accepted = message.getString("requestAccept").equals("1");
    String id = message.getString("id");
    String name = message.getString("name");
    String email = message.getString("partner");
    String msg = name + " " + (accepted ? "accepted" : "rejected") + " your request!";

    sendNotification(context, title, msg);

    //Handle accept
    if (accepted) {
      app.getLocalUser().setPartner(id);
    }
  }

  /**
   * Gets the ID of Partner Response
   * @return String - ID of partner response
   */
  protected void sendNotification(Context context, String title, String msg) {
    // Send notification
    new Notification(context)
        .setIntent(new Intent(context, MainActivity.class))
        .setTitle(title)
        .setMsg(msg)
        .show();
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_RESPONSE.value;
  }
}

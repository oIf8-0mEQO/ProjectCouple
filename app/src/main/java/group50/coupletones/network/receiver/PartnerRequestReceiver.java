package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;

import group50.coupletones.R;
import group50.coupletones.controller.PartnerResponseActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
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

  /**
   * onReceive of Partner Request
   * @param message The incoming message object that contains data from the server
   */
  @Override
  public void onReceive(Message message) {
    // The partner making the request
    String partnerName = message.getString("name");
    String partnerEmail = message.getString("partner");

    String title = context.getString(R.string.partner_request_header);
    String msg = partnerEmail + " " + R.string.partner_up_text;

    // Bundle the data into the intent when opening MainActivity
    Intent intent = new Intent(context, PartnerResponseActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("name", partnerName);
    intent.putExtra("email", partnerEmail);

    new Notification(context)
      .setIntent(intent)
      .setTitle(title)
      .setMsg(msg)
      .show();
  }

  /**
   * Gets the ID of partner request
   * @return String - ID of partner request
   */
  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_REQUEST.value;
  }
}

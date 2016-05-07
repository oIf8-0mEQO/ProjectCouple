package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.controller.PartnerRequestActivity;
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

  @Override
  public void onReceive(Message message) {
    // The partner making the request
    String partnerName = message.getString("name");
    String partnerEmail = message.getString("partner");


    //TODO: Use values/strings
    String title = "Partner Request";
    String msg = partnerEmail + " wants to partner with you!";

    // Bundle the data into the intent when opening MainActivity
    Intent intent = new Intent(context, PartnerRequestActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("name", partnerName);
    intent.putExtra("email", partnerEmail);

    new Notification(context)
      .setIntent(intent)
      .setTitle(title)
      .setMsg(msg)
      .show();
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_REQUEST.value;
  }
}

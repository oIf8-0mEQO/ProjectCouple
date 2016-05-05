package group50.coupletones.network.receiver;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
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
public class PartnerRejectReceiver implements MessageReceiver, Identifiable {

  private final Context context;
  private Handler handler = new Handler();

  public PartnerRejectReceiver(Context context) {
    this.context = context;
  }

  @Override
  public void onReceive(Message message) {
    // The partner making the request
    String error = message.getString("error");
    //TODO: Use values/strings
    //TODO: Check if app is on?
    handler.post(() -> Toast.makeText(context, error, Toast.LENGTH_LONG).show());
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_REJECT.value;
  }
}

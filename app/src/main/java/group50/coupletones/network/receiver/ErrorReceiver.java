package group50.coupletones.network.receiver;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;

/**
 * Handles general error request.
 *
 * @author Henry Mao
 * @since 5/4/16
 */
public class ErrorReceiver implements MessageReceiver {

  private final Context context;
  private Handler handler = new Handler();

  public ErrorReceiver(Context context) {
    this.context = context;
  }

  /**
   * onReceive for error reception
   * @param message - The incoming message object that
   *                contains data from the server
   */
  @Override
  public void onReceive(Message message) {
    // The partner making the request
    String error = message.getString("error");
    //TODO: Use values/strings
    //TODO: Check if app is on?
    handler.post(() -> Toast.makeText(context, error, Toast.LENGTH_LONG).show());
  }
}

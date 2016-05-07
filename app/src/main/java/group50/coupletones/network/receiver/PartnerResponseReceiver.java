package group50.coupletones.network.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageReceiver;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.util.Identifiable;
import group50.coupletones.util.storage.Storage;

import static android.content.Context.MODE_PRIVATE;

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

  @Override
  public void onReceive(Message message) {
    // Phone notification
    String title = "Partner Request";
    boolean accepted = message.getBoolean("requestAccept");
    String name = message.getString("name");
    String email = message.getString("partner");
    String msg = name + " " + (accepted ? "accepted" : "rejected") + " your request!";

    // Send notification
    new Notification(context)
      .setIntent(new Intent(context, MainActivity.class))
      .setTitle(title)
      .setMsg(msg)
      .show();

    //Handle accept
    if (accepted) {
      app.getLocalUser().setPartner(new Partner(name, email));
      app.getLocalUser().save(new Storage(context.getSharedPreferences("user", MODE_PRIVATE)));
    }
  }

  @Override
  public String getId() {
    return MessageType.RECEIVE_PARTNER_RESPONSE.value;
  }
}

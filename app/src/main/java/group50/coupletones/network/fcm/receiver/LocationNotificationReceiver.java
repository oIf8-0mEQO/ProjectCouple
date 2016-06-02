package group50.coupletones.network.fcm.receiver;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.network.fcm.message.Message;
import group50.coupletones.network.fcm.message.MessageReceiver;
import group50.coupletones.network.fcm.message.MessageType;
import group50.coupletones.util.Identifiable;
import rx.Observable;

/**
 * @author Sharmaine Manalo
 * @since 5/5/16
 */

/**
 * Location notification receiver class
 */
public class LocationNotificationReceiver implements MessageReceiver, Identifiable {

  private final Context context;

  private final CoupleTones app;

  /**
   * Location Notification Receiver
   *
   * @param app - Coupletones
   */
  public LocationNotificationReceiver(CoupleTones app, Context context) {
    this.context = context;
    this.app = app;
  }

  /**
   * onReceive for location notification
   *
   * @param message - The incoming message object that
   *                contains data from the server
   */
  @Override
  public void onReceive(Message message) {
    Notification notification = new Notification(context);
    notification.setTitle(context.getString(R.string.app_name));
    Observable<Partner> partnerObserver = app.getLocalUser().getPartner();
    partnerObserver
      .subscribe(partner -> {
        notification.setMsg(
          partner.getName() + " " +
            context.getString(R.string.partner_visited_text) + " " +
            message.getString("name")
        );

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notification.setIntent(intent);
        notification.show();
      });
  }

  /**
   * Gets the ID of notification
   *
   * @return - String of notification
   */
  @Override
  public String getId() {
    return MessageType.RECEIVE_MAP_NOTIFY.value;
  }
}

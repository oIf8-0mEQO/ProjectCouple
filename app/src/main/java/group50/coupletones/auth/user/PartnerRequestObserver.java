package group50.coupletones.auth.user;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.R;
import group50.coupletones.controller.PartnerResponseActivity;
import group50.coupletones.network.receiver.Notification;

import java.util.List;

/**
 * Observes whenever a partner request is added to the user.
 * The observer notifies the user of this request.
 *
 * @author Henry Mao
 * @since 5/25/16
 */
public class PartnerRequestObserver {

  private final Context context;
  private UserFactory factory;
  private LocalUser localUser;

  public PartnerRequestObserver(Context context, UserFactory factory) {
    this.context = context;
    this.factory = factory;
  }

  /**
   * Binds the observer to act on a given user
   *
   * @param localUser The user to observe
   * @return Self instance
   */
  public PartnerRequestObserver bind(ConcreteUser localUser) {
    this.localUser = localUser;
    localUser
      .getSync()
      .get("partnerRequests")
      .subscribe(this::onRequestsChange);

    return this;
  }

  /**
   * Called when the list of partner request changes.
   *
   * @param change
   */
  public void onRequestsChange(Object change) {
    // Get the end of the partner list
    List<String> partnerRequests = localUser.getPartnerRequests();

    if (partnerRequests.size() > 0) {
      String firstUserId = partnerRequests.listIterator().next();

      // Retrieve the partner object
      LocalUser partner = factory.withId(firstUserId).build();

      // Bundle the data into the intent when opening MainActivity
      Intent intent = new Intent(context, PartnerResponseActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.putExtra("id", partner.getId());
      intent.putExtra("name", partner.getName());
      intent.putExtra("email", partner.getEmail());

      String title = context.getString(R.string.partner_request_header);
      String msg = partner.getEmail() + " " + context.getString(R.string.partner_up_text);

      new Notification(context)
        .setIntent(intent)
        .setTitle(title)
        .setMsg(msg)
        .show();
    }
  }
}

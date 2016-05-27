package group50.coupletones.auth.user;

import android.content.Context;
import android.content.Intent;
import group50.coupletones.R;
import group50.coupletones.controller.PartnerResponseActivity;
import group50.coupletones.network.receiver.Notification;
import rx.Observable;

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
  public PartnerRequestObserver bind(LocalUser localUser) {
    this.localUser = localUser;
    localUser
      .getProperties()
      .property("partnerRequests")
      .observable()
      .distinctUntilChanged()
      .subscribe(this::onRequestsChange);

    return this;
  }

  /**
   * Called when the list of partner request changes.
   *
   * @param change The object that changes
   */
  public void onRequestsChange(Object change) {
    // Get the end of the partner list
    if (change instanceof List) {
      List<String> partnerRequests = (List) change;

      if (partnerRequests.size() > 0) {
        String firstUserId = partnerRequests.listIterator().next();

        // Retrieve the partner object
        User partner = factory.withId(firstUserId).build();

        // Wait until all data is received.
        //TODO: Maybe add onLoaded to user?
        Observable<User> basicData = Observable.zip(
          partner.getProperties().property("id").observable(),
          partner.getProperties().property("name").observable(),
          partner.getProperties().property("email").observable(),
          (a, b, c) -> partner
        );

        // When the basic data is synced from DB, we can then create the notification
        basicData
          .skip(1)
          .first()
          .subscribe(syncedPartner -> {
            // Bundle the data into the intent when opening MainActivity
            Intent intent = new Intent(context, PartnerResponseActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", syncedPartner.getId());
            intent.putExtra("name", syncedPartner.getName());
            intent.putExtra("email", syncedPartner.getEmail());

            String title = context.getString(R.string.partner_request_header);
            String msg = syncedPartner.getEmail() + " " + context.getString(R.string.partner_up_text);

            new Notification(context)
              .setIntent(intent)
              .setTitle(title)
              .setMsg(msg)
              .show();
          });
      }
    }
  }
}

package group50.coupletones.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.message.OutgoingMessage;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

/**
 * Activity for Partner Response on the app.
 */
public class PartnerResponseActivity extends Activity {

  @Inject
  public CoupleTones app;

  @Inject
  public NetworkManager network;

  /**
   * onCreate
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Dependency Injection
    CoupleTones.global().inject(this);

    setContentView(R.layout.activity_partner_request);

    //TODO this is the custom font setup
    TextView partnerName = (TextView) findViewById(R.id.requested_partner);
    TextView requestText = (TextView) findViewById(R.id.request_text);
    Button acceptButton = (Button) findViewById(R.id.accept_button);
    Button rejectButton = (Button) findViewById(R.id.reject_button);

    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));

    partnerName.setTypeface(pierSans);
    requestText.setTypeface(pierSans);
    acceptButton.setTypeface(pierSans);
    rejectButton.setTypeface(pierSans);

    // Process partner add requests
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null && extras.containsKey("name") && extras.containsKey("email")) {
      // The user tapped on the notification.
      // This means the user wants to add a new partner
      String name = extras.getString("name");
      String email = extras.getString("email");
      partnerName.setText(name);
      requestText.setText(email + " " + R.string.partner_up_text);

      acceptButton.setOnClickListener(click -> sendResponse(name, email, true));
      rejectButton.setOnClickListener(click -> sendResponse(name, email, false));
    } else {
      // Invalid data. Close the activity.
      finish();
    }
  }

  /**
   * Sends a response to the partner request.
   * @param name - Partner's name
   * @param email - Partner's email
   * @param accept - accept or reject request
   */
  private void sendResponse(String name, String email, boolean accept) {
    // Send a partner request to the server
    network.send(
      (OutgoingMessage)
        new OutgoingMessage(MessageType.SEND_PARTNER_RESPONSE.value)
          .setString("partner", email)
          .setString("requestAccept", accept ? "1" : "0")
    );

    if (accept) {
      app.getLocalUser().setPartner(new Partner(name, email));
      app.getLocalUser().save(new Storage(getSharedPreferences(Storage.PREF_FILE_USER, MODE_PRIVATE)));
    }

    finish();
  }
}

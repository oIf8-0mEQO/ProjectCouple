package group50.coupletones.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.auth.user.UserFactory;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.util.Taggable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

/**
 * Activity for Adding a Partner
 */
public class AddPartnerActivity extends AppCompatActivity implements Taggable {

  @Inject
  public NetworkManager network;

  @Inject
  public CoupleTones app;

  @Inject
  public UserFactory userFactory;

  /**
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    CoupleTones.global().inject(this);

    setContentView(R.layout.activity_add_partner);

    //TODO: Clean this up
    TextView add_partner_text = (TextView) findViewById(R.id.connect_message);
    TextView email_address_text = (TextView) findViewById(R.id.email_address);
    TextView connect_text = (TextView) findViewById(R.id.connect_button);
    TextView skip_text = (TextView) findViewById(R.id.skip_button);

    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));

    add_partner_text.setTypeface(pierSans);
    email_address_text.setTypeface(pierSans);
    connect_text.setTypeface(pierSans);
    skip_text.setTypeface(pierSans);

    // Connect button
    findViewById(R.id.connect_button).setOnClickListener(this::onClickConnect);

    findViewById(R.id.skip_button)
      .setOnClickListener(view -> finish());
  }

  private void onClickConnect(View view) {
    // Send a partner request
    String email = ((EditText) findViewById(R.id.email_address)).getText().toString();

    userFactory
      .withEmail(email)  // Get user by email
      .subscribeOn(Schedulers.newThread())
      .flatMap(buildable -> buildable.build().load())
      .subscribe(
        partner -> {
          // Found user! Set the email
          Toast.makeText(AddPartnerActivity.this, "Request sent.", Toast.LENGTH_SHORT).show();
          ((Partner) partner).requestPartner(app.getLocalUser());
          finish();
        },
        // User does not exist.
        error -> Toast.makeText(AddPartnerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()
      );
  }
}

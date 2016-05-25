package group50.coupletones.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.ConcreteUser;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.sync.FirebaseSync;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * Activity for Adding a Partner
 */
public class AddPartnerActivity extends AppCompatActivity
  implements View.OnClickListener, Taggable {

  @Inject
  public NetworkManager network;

  @Inject
  public CoupleTones app;

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

    findViewById(R.id.connect_button).setOnClickListener(this);
    findViewById(R.id.skip_button).setOnClickListener(this);
  }

  /**
   * onClick for Adding Partner Activity
   *
   * @param v - The current view
   */
  public void onClick(View v) {
    switch (v.getId()) {

      // Switches to AddPartnerActivity.
      case R.id.connect_button:
        // Send a partner request
        // TODO: Not unit testable, when ref firebase
        DatabaseReference ref = ConcreteUser.getDatabase();
        String email = ((EditText) findViewById(R.id.email_address)).getText().toString();

        // Find the user by email
        ref.getRef()
          .orderByChild("email")
          .equalTo(email)
          .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.getValue() != null) {
                DatabaseReference partnerDb = dataSnapshot.getChildren().iterator().next().getRef();
                LocalUser partner = new ConcreteUser(new FirebaseSync().setRef(partnerDb));
                partner.requestPartner(app.getLocalUser());
                finish();
              } else {
                // User does not exist.
                Toast.makeText(AddPartnerActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
          });
        break;
      // Switches to AddPartnerActivity.
      case R.id.skip_button:
        finish();
        Log.d(getTag(), "Switched to SettingsFragment Successfully");
        break;
    }
  }
}

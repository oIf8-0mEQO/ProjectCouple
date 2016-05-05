package group50.coupletones.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.tab.SettingsFragment;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

public class AddPartnerActivity extends AppCompatActivity
  implements View.OnClickListener, Taggable {

  @Inject
  public NetworkManager network;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    CoupleTones.component().inject(this);

    setContentView(R.layout.activity_add_partner);

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

  public void onClick(View v) {
    switch (v.getId()) {

      // Switches to AddPartnerActivity.
      case R.id.connect_button:
        /** TODO: SEND REQUEST to partner
         *
         * -GCM calls?
         * -After request goes through, send a toast(saying partner has been notified?)
         * -Switch to SettingsFragment
         *
         */


        break;

      // Switches to AddPartnerActivity.
      case R.id.skip_button:
        Intent i = new Intent(AddPartnerActivity.this, SettingsFragment.class);

        Log.d(getTag(), "Switched to SettingsFragment Successfully");
        break;
    }
  }
}

package group50.coupletones.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import group50.coupletones.R;

public class AddPartnerActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView add_partner_text = (TextView) findViewById(R.id.connect_message);
    TextView email_address_text = (TextView) findViewById(R.id.email_address);
    TextView connect_text = (TextView) findViewById(R.id.connect_button);
    TextView skip_text = (TextView) findViewById(R.id.skip_button);



    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));

    add_partner_text.setTypeface(pierSans);
    email_address_text.setTypeface(pierSans);
    connect_text.setTypeface(pierSans);
    skip_text.setTypeface(pierSans);

    setContentView(R.layout.activity_add_partner);
  }

}

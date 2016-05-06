package group50.coupletones.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import group50.coupletones.R;

public class PartnerRequestActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_partner_request);

    //TODO this is the custom font setup
    TextView partner_name_text = (TextView) findViewById(R.string.partner_name);
    TextView partner_request_text = (TextView) findViewById(R.string.partner_request);
    TextView accept_text = (TextView) findViewById(R.id.accept_button);
    TextView reject_text = (TextView) findViewById(R.id.reject_button);

    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));

    partner_name_text.setTypeface(pierSans);
    partner_request_text.setTypeface(pierSans);
    accept_text.setTypeface(pierSans);
    reject_text.setTypeface(pierSans);
  }

}

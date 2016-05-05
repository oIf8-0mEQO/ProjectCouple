package group50.coupletones.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import group50.coupletones.R;
import group50.coupletones.controller.tab.SettingsFragment;

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

    Button button = (Button) findViewById(R.id.connect_button);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Log.i("clicks", "You Clicked B1");
        Intent i = new Intent(
          AddPartnerActivity.this,
          SettingsFragment.class);
        startActivity(i);
      }
    });
  }
}

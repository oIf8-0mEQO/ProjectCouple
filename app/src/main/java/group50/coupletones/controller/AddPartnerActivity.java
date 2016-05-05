package group50.coupletones.controller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import group50.coupletones.R;
import group50.coupletones.controller.tab.SettingsFragment;

public class AddPartnerActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_partner);

    Button button = (Button) findViewById(R.id.connect_button);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Log.i("clicks","You Clicked B1");
        Intent i=new Intent(
                AddPartnerActivity.this,
                SettingsFragment.class);
        startActivity(i);
      }
  });
  }}

/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.GoogleUser;

/**
 * The LoginActivity controls the login page of the app.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
  /**
   * The tag used for this activity
   */
  private static final String TAG = "LoginActivity";

  private Authenticator<GoogleAuthenticator, GoogleUser, ConnectionResult> auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    // Create Google Authenticator for automatic sign in.
    auth = new GoogleAuthenticator(this);
    auth.autoSignIn();

    TextView coupleTones_text = (TextView) findViewById(R.id.sign_in_button);
    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
    coupleTones_text.setTypeface(pierSans);
    findViewById(R.id.sign_in_button).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      // When the sign in button is clicked
      case R.id.sign_in_button:
        auth.signIn();
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    auth.onActivityResult(requestCode, resultCode, data);
  }
}

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
import android.widget.Toast;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.User;
import group50.coupletones.di.DaggerMainComponent;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;

/**
 * The LoginActivity controls the login page of the app.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Taggable {

  /**
   * The object used for authentication
   */
  @Inject
  public Authenticator<GoogleAuthenticator, User, String> auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Dependency Injection
    DaggerMainComponent.create().inject(this);

    setContentView(R.layout.activity_login);

    // Create Google Authenticator for automatic sign in.
    auth.bind(this);
    auth.autoSignIn();
    auth.onSuccess(this::onUserLogin);

    TextView coupleTones_text = (TextView) findViewById(R.id.sign_in_button);
    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
    coupleTones_text.setTypeface(pierSans);
    findViewById(R.id.sign_in_button).setOnClickListener(this);
  }

  private User onUserLogin(User user) {
    //TODO: Better design to signify how the sign in failed. User should never be null?
    if (user != null) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    } else {
      //TODO: Remove? For debug. Use string constant
      Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
    }
    return user;
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

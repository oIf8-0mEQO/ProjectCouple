/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

/**
 * The LoginActivity controls the login page of the app.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Taggable {

  /**
   * The object used for authentication
   */
  @Inject
  public Authenticator<User, String> auth;

  @Inject
  public CoupleTones app;

  @Inject
  public GoogleApiClient apiClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Dependency Injection
    CoupleTones.component().inject(this);

    setContentView(R.layout.activity_login);

    // Create Google Authenticator for automatic sign in.
    auth.onSuccess(this::onUserLogin);
    auth.autoSignIn();

    TextView coupleTones_text = (TextView) findViewById(R.id.sign_in_button);
    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
    coupleTones_text.setTypeface(pierSans);
    findViewById(R.id.sign_in_button).setOnClickListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    apiClient.connect();
  }

  @Override
  protected void onStop() {
    super.onStop();
    apiClient.disconnect();
  }

  /**
   * Handles the user login event by switching to MainActivity upon
   * successful login.
   * @param user The user that logged in
   * @return The user
   */
  private User onUserLogin(User user) {
    //TODO: Better design to signify how the sign in failed. User should never be null?
    if (user != null) {
      // Save the user
      app.getLocalUser().load(new Storage(getSharedPreferences("user", Context.MODE_PRIVATE)));

      // Star the main activity
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    } else {
      //TODO: Use string constant
      Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
    }
    return user;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      // When the sign in button is clicked
      case R.id.sign_in_button:
        auth.signIn(this);
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    auth.onActivityResult(requestCode, resultCode, data);
  }
}

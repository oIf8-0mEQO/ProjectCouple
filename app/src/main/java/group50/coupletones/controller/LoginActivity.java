/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.controller;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.User;
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
  public Authenticator<User, String> auth;
  // TODO: CLEAN UP CODE
  private String PROJECT_NUMBER = "794558589013";
  private GoogleCloudMessaging gcm;
  private String regid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Dependency Injection
    CoupleTones.component().inject(this);

    setContentView(R.layout.activity_login);

    // Create Google Authenticator for automatic sign in.
    auth.bind(this);
    auth.onSuccess(this::onUserLogin);
    auth.autoSignIn();

    TextView coupleTones_text = (TextView) findViewById(R.id.sign_in_button);
    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
    coupleTones_text.setTypeface(pierSans);
    findViewById(R.id.sign_in_button).setOnClickListener(this);

    getRegId();
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
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
      Log.d(getTag(), "Logged in successfully");
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

  public void getRegId() {
    // TODO
    new AsyncTask<Void, Void, String>() {

      @Override
      protected String doInBackground(Void... params) {
        String msg = "";
        try {
          if(gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
          }

          regid = gcm.register(PROJECT_NUMBER);
          msg = "Device registered, registration ID=" + regid;
          Log.i("GCM", "!!!!! " + regid);

          //TODO DELETE
          try {
            Bundle data = new Bundle();
            data.putString("email", "hello@sharmaine.me");
            data.putString("my_action","SAY_HELLO");
            String id = "3722ewhdjklhksand1no";
            gcm.send(PROJECT_NUMBER + "@gcm.googleapis.com", id, data);
            msg = "Sent message";
          } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
          }
          return msg;

        } catch(IOException ex) {
          msg = "Error: " + ex.getMessage();
        }
        return msg;
      }

      @Override
      protected void onPostExecute(String msg) {
        Log.d(getTag(), "Debug message");
      }
    }.execute(null, null, null);
  }
}

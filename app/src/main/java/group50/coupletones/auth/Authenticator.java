package group50.coupletones.auth;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.api.Status;
import group50.coupletones.util.callback.FailCallback;
import group50.coupletones.util.callback.SuccessCallback;
import group50.coupletones.util.function.Consumer;

/**
 * An abstraction for objects that handle user authentication.
 * @author Henry Mao
 */
public interface Authenticator<S, F> extends SuccessCallback<Authenticator, S>, FailCallback<Authenticator, F> {
  /**
   * Attempts to sign in the user automatically by using silent
   * sign in.
   * @return This instance
   */
  Authenticator autoSignIn();

  /**
   * Attempts to sign in the user by prompting the user for login
   * credentials.
   * @param activity The activity that is starting the sign in process
   * @return This instance
   */
  Authenticator signIn(Activity activity);

  /**
   * Attempts to sign in the user by prompting the user for login
   * credentials.
   * @return This instance
   */
  GoogleAuthenticator signOut(Consumer<Status> consumer);

  /**
   * Method that handles the intent result callback.
   * <p>
   * This method must be called to by the activity to properly handle
   * intents returning a result.
   * @param requestCode The request code of the intent
   * @param resultCode The result code of the intent
   * @param data The data of the intent
   */
  void onActivityResult(int requestCode, int resultCode, Intent data);

}

/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones;

import android.app.Application;
import group50.coupletones.auth.User;

/**
 * A singleton object that holds global data.
 * Represents the main Android application.
 * Lifecycle of instance persists as long as the app is running.
 */
//TODO: Consider dependency injection framework if too many singletons
public class CoupleTones extends Application {

  /**
   * The static instance of App.
   */
  private static CoupleTones instance;
  /**
   * The local user of the app
   */
  private User localUser;

  /**
   * @return The instance of the app singleton
   */
  public static CoupleTones instance() {
    return instance;
  }

  /**
   * @return The local user. If the user is not logged in, null is returned.
   */
  public User getLocalUser() {
    return localUser;
  }

  /**
   * Sets the local user of the app. This method should only be
   * during login/logout events.
   * @param localUser The local user object
   */
  public void setLocalUser(User localUser) {
    this.localUser = localUser;
  }

  /**
   * @return True if and only if the local user is logged into the app.
   */
  public boolean isLoggedIn() {
    return localUser != null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }
}

/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones;

import group50.coupletones.auth.User;

/**
 * A singleton object that holds global data.
 */
public class App {
    private static final App instance = new App();
    /**
     * The local user of the app
     */
    private User localUser;

    /**
     * @return The instance of the app singleton
     */
    public static App instance() {
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
     *
     * @param localUser The local user object
     */
    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

}

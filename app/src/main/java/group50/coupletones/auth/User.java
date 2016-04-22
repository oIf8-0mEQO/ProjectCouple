/**
 * @author Henry Mao
 * @since 4/22/16.
 */

package group50.coupletones.auth;

/**
 * The user represents a single user in the app.
 */
public class User {
    /**
     * The id of the user. Immutable.
     */
    private final String id;

    /**
     * The name of the user. Immutable.
     */
    private final String name;

    /**
     * The email of the user. Immutable
     */
    private final String email;

    /**
     * Creates a new user with a given name and email.
     *
     * @param id    The idof the user
     * @param name  The name of the user
     * @param email The email of the user
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }
}

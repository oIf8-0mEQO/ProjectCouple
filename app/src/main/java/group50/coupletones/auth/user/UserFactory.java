package group50.coupletones.auth.user;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import group50.coupletones.network.sync.FirebaseSync;
import group50.coupletones.network.sync.Sync;

import javax.inject.Inject;

/**
 * A factory object responsible for constructing User instances.
 *
 * @author Henry Mao
 * @since 5/25/16
 */
public class UserFactory {
  @Inject
  public UserFactory() {
  }

  /**
   * Creates a user from an existing user.
   *
   * @param userId The ID of the user
   * @return Self instance
   */
  public Buildable withId(String userId) {
    Buildable buildable = new Buildable();
    buildable.sync.setRef(getDatabase().child(userId));
    return buildable;
  }

  /**
   * Creates a new user from a Google account
   *
   * @param account The Google account
   * @return Self instance
   */
  public Buildable createNew(GoogleSignInAccount account) {
    Buildable buildable = new Buildable();
    buildable.sync.setRef(getDatabase().child(account.getId()));
    buildable.sync.getRef().child("id").setValue(account.getId());
    buildable.sync.getRef().child("name").setValue(account.getDisplayName());
    buildable.sync.getRef().child("email").setValue(account.getEmail());
    return buildable;
  }

  protected Sync buildSync() {
    return new FirebaseSync();
  }

  public DatabaseReference getDatabase() {
    return FirebaseDatabase.getInstance().getReference("users");
  }

  public class Buildable {
    private final Sync sync;
    private boolean built = false;

    public Buildable() {
      sync = buildSync();
      sync.setRef(getDatabase());
    }

    public LocalUser build() {
      if (built)
        throw new IllegalStateException("Cannot build a factory twice.");

      built = true;

      return new ConcreteUser(sync);
    }
  }
}

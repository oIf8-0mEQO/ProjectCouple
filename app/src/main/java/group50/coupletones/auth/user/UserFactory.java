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

  private Sync sync;

  @Inject
  public UserFactory() {
    sync = buildSync();
    sync.setRef(getDatabase());
  }

  /**
   * Creates a user from an existing user.
   *
   * @param userId The ID of the user
   * @return Self instance
   */
  public UserFactory withId(String userId) {
    sync.setRef(getDatabase().child(userId));
    return this;
  }

  /**
   * Creates a new user from a Google account
   *
   * @param account The Google account
   * @return Self instance
   */
  public UserFactory createNew(GoogleSignInAccount account) {
    sync.setRef(getDatabase().child(account.getId()));
    sync.getRef().child("id").setValue(account.getId());
    sync.getRef().child("name").setValue(account.getDisplayName());
    sync.getRef().child("email").setValue(account.getEmail());
    return this;
  }

  protected Sync buildSync() {
    return new FirebaseSync();
  }

  public DatabaseReference getDatabase() {
    return FirebaseDatabase.getInstance().getReference("users");
  }

  public LocalUser build() {
    return new ConcreteUser(sync);
  }
}

package group50.coupletones.auth.user;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import group50.coupletones.auth.user.concrete.ConcreteLocalUser;
import group50.coupletones.auth.user.concrete.ConcretePartner;
import group50.coupletones.network.sync.FirebaseSync;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.function.Function;

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
  public Buildable<Partner> withId(String userId) {
    return new Buildable<>(ConcretePartner::new, buildSync().child(userId));
  }

  /**
   * Creates a new user from a Google account
   *
   * @param account The Google account
   * @return Self instance
   */
  public Buildable<ConcreteLocalUser> withAccount(GoogleSignInAccount account) {
    FirebaseSync sync = (FirebaseSync) buildSync().child(account.getId());
    sync.getRef().child("id").setValue(account.getId());
    sync.getRef().child("name").setValue(account.getDisplayName());
    sync.getRef().child("email").setValue(account.getEmail());
    return new Buildable<>(ConcreteLocalUser::new, sync);
  }

  protected Sync buildSync() {
    return new FirebaseSync();
  }

  public DatabaseReference getDatabase() {
    return FirebaseDatabase.getInstance().getReference("users");
  }

  public class Buildable<T extends User> {
    private final Function<Sync, T> constructor;
    private boolean built = false;
    private Sync sync;

    public Buildable(Function<Sync, T> constructor, Sync sync) {
      this.sync = sync;
      this.constructor = constructor;
    }

    public T build() {
      if (built) {
        throw new IllegalStateException("Cannot build a factory twice.");
      }

      built = true;
      return constructor.apply(sync);
    }
  }
}

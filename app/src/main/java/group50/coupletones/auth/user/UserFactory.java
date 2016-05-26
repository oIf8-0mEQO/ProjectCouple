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
 * @author Henry Mao
 * @since 5/25/16
 */
public class UserFactory {
  @Inject
  public UserFactory() {
  }

  public Buildable<Partner> withDB(DatabaseReference db) {
    Buildable<Partner> buildable = new Buildable<>(ConcretePartner::new);
    buildable.sync.setRef(db);
    return buildable;
  }

  /**
   * Creates a user from an existing user.
   * @param userId The ID of the user
   * @return Self instance
   */
  public Buildable<Partner> withId(String userId) {
    return withDB(getDatabase().child(userId));
  }

  /**
   * Creates a new user from a Google account
   * @param account The Google account
   * @return Self instance
   */
  public Buildable<ConcreteLocalUser> withAccount(GoogleSignInAccount account) {
    Buildable<ConcreteLocalUser> buildable = new Buildable<>(ConcreteLocalUser::new);
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

  public class Buildable<T extends User> {
    private final Sync sync;
    private final Function<Sync, T> constructor;
    private boolean built = false;

    public Buildable(Function<Sync, T> constructor) {
      sync = buildSync();
      sync.setRef(getDatabase());
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

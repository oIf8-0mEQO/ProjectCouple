package group50.coupletones.auth.user;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import group50.coupletones.auth.user.concrete.ConcreteLocalUser;
import group50.coupletones.auth.user.concrete.ConcretePartner;
import group50.coupletones.network.sync.FirebaseSync;
import group50.coupletones.network.sync.Sync;
import group50.coupletones.util.function.Function;
import rx.Observable;

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

  /**
   * Creates a user from an existing user.
   * @param userId The ID of the user
   * @return Self instance
   */
  public Buildable<Partner> withId(String userId) {
    return new Buildable<>(ConcretePartner::new, buildSync().child(userId));
  }

  /**
   * Creates a new user from a Google account
   * @param account The Google account
   * @return Self instance
   */
  public Buildable<LocalUser> withAccount(GoogleSignInAccount account) {
    FirebaseSync sync = (FirebaseSync) buildSync().child(account.getId());
    sync.getRef().child("id").setValue(account.getId());
    sync.getRef().child("name").setValue(account.getDisplayName());
    sync.getRef().child("email").setValue(account.getEmail());
    sync.getRef().child("fcmToken").setValue(FirebaseInstanceId.getInstance().getToken());
    return new Buildable<>(ConcreteLocalUser::new, sync);
  }

  /**
   * Finds a user by email
   * @param email The email of the user
   * @return A observable that will return the value when user is found (async).
   */
  public Observable<Buildable<Partner>> withEmail(String email) {
    FirebaseSync ref = (FirebaseSync) buildSync();

    // Find the user by email
    return Observable.create(act ->
      ref.getRef()
        .orderByChild("email")
        .equalTo(email)
        .addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
              // Gets the partner's DB
              DatabaseReference partnerDb = dataSnapshot
                .getChildren()
                .iterator()
                .next()
                .getRef();

              act.onNext(withId(partnerDb.getKey()));
              act.onCompleted();
              return;
            }
            act.onError(new Throwable("Invalid Email"));
            act.onCompleted();
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        })
    );
  }

  public Sync buildSync() {
    return new FirebaseSync().child("users");
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

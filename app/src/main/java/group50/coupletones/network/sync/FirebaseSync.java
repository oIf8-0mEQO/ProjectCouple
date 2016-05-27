package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.*;
import group50.coupletones.util.observer.Properties;
import group50.coupletones.util.observer.Property;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Observes fields and syncs fields with Firebase
 *
 * @author Henry Mao
 */
public class FirebaseSync implements Sync {

  protected final DatabaseReference ref;

  private final List<ValueEventListener> listeners = new LinkedList<>();

  public FirebaseSync() {
    this(FirebaseDatabase.getInstance().getReference());
  }

  public FirebaseSync(DatabaseReference ref) {
    this.ref = ref;
  }

  protected void verify() {
    if (ref == null)
      throw new IllegalStateException("Database reference is null.");
  }

  @Override
  public Sync watchAll(Properties properties) {
    verify();
    Collection<Property<?>> all = properties.all();

    for (Property<?> property : all) {
      watch(property);
    }
    return this;
  }

  //TODO: Ability to unwatch
  @Override
  public Sync watch(Property<?> property) {
    verify();

    // When Firebase changes for this property, we want to know about it.
    listeners.add(
      ref.child(property.name())
        .addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            Log.v("FirebaseSync", "Receive " + property.name() + " => " + dataSnapshot.getValue());
            ((Property) property).observable().onNext(dataSnapshot.getValue());
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
          }
        })
    );

    // When the property changes, we want to tell update Firebase about it
    property
      .observable()
      .subscribe(obj -> {
        // TODO: Depending on property type, subscribe differently.
        Log.v("FirebaseSync", "Send " + property.name() + " with " + property.get());
        ref.child(property.name()).setValue(property.get());
      });

    return this;
  }

  @Override
  public Sync parent() {
    return new FirebaseSync(ref.getParent());
  }

  @Override
  public Sync child(String name) {
    return new FirebaseSync(ref.child(name));
  }

  public DatabaseReference getRef() {
    return ref;
  }
}

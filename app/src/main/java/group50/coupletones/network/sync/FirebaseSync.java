package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import rx.Observable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that handles Firebase real time database syncing for objects.
 *
 * @author Henry Mao
 * @since 5/22/16
 */
//TODO: Unit test
//TODO: Refactor to field publisher, generic. Returns an interface for publishing
public class FirebaseSync implements Sync {

  /**
   * The object to sync
   */
  protected Object obj;

  protected DatabaseReference ref;

  protected Map<String, Field> syncFields;

  protected Map<String, Observable<?>> observables;

  /**
   * Sets the Sync object to watch a particular object
   *
   * @param obj The object to watch
   * @return Self instance
   */
  @Override
  public FirebaseSync watch(Object obj) {
    this.obj = obj;
    Log.d("FirebaseSync", this + " watching " + obj);
    return this;
  }

  @Override
  public DatabaseReference getRef() {
    return ref;
  }

  /**
   * Sets the Sync object to refer a particular database
   *
   * @param ref The database use
   * @return Self instance
   */
  @Override
  public FirebaseSync setRef(DatabaseReference ref) {
    this.ref = ref;
    return this;
  }

  protected void verifyRefAndObjSet() {
    if (obj == null || ref == null)
      throw new IllegalStateException("Object or reference null. Unable to sync.");
  }

  /**
   * Cache all fields of the given object that requires syncing.
   * Automatically generates observables for every single field.
   */
  protected void build() {
    verifyRefAndObjSet();
    if (syncFields == null) {
      syncFields = new HashMap<>();
      observables = new HashMap<>();

      Field[] fields = obj.getClass().getDeclaredFields();

      for (Field field : fields) {
        if (field.isAnnotationPresent(Syncable.class)) {
          // Force accessibility
          field.setAccessible(true);
          String name = field.getName();

          if (syncFields.containsKey(name))
            throw new IllegalStateException("Duplicate field name.");

          syncFields.put(name, field);

          // Creates an observable that listens to Firebase data change.
          Observable<?> observable = Observable.create(act -> {
            ref
              .child(name)
              .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  act.onNext(dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  throw databaseError.toException();
                }
              });
          });

          observables.put(name, observable);
        }
      }
    }
  }

  /**
   * Gets the observable associated with this field.
   *
   * @param fieldName The name of the field in this class.
   * @return An observable object.
   */
  @Override
  public Observable<?> get(String fieldName) {
    build();
    if (observables.containsKey(fieldName))
      return observables.get(fieldName);
    else
      throw new IllegalArgumentException("Field name: " + fieldName + " does not have @Sycnable annotation.");
  }

  /**
   * Binds a Firebase listener that automatically updates the fields.
   * upon data change.
   */
  public Sync subscribeAll() {
    verifyRefAndObjSet();
    build();

    // Add a listener for each field
    for (String fieldName : syncFields.keySet()) {
      subscribe(fieldName);
    }

    return this;
  }

  /**
   * Subscribes a field in the class to receive updates from the database automatically.
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Sync subscribe(String fieldName) {
    Observable<?> observable = get(fieldName);
    Field field = syncFields.get(fieldName);
    observable.subscribe(change -> {
      try {
        Log.d("FirebaseSync", "Receieved " + fieldName + " with " + change);
        field.set(obj, change);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return this;
  }

  /**
   * Attempts to sync a specific field to the server
   *
   * @param fieldNames The name of the field
   * @return Self instance
   */
  @Override
  public Sync publish(String... fieldNames) {
    build();

    for (String fieldName : fieldNames) {
      if (syncFields.containsKey(fieldName))
        publish(syncFields.get(fieldName));
      else
        throw new IllegalArgumentException("Field name: " + fieldName + " does not have @Sycnable annotation.");
    }
    return this;
  }

  /**
   * Publishes all fields to the database.
   *
   * @return Self instance
   */
  @Override
  public Sync publishAll() {
    build();

    // Add a listener for each field
    for (String fieldName : syncFields.keySet()) {
      publish(fieldName);
    }

    return this;
  }

  /**
   * Attempts to sync a specific field to the server
   *
   * @param field The field instance
   * @return Self instance
   */
  protected Sync publish(Field field) {
    build();

    try {
      Log.d("FirebaseSync", "Publishing: " + field.getName() + " with: " + field.get(obj));
      ref.child(field.getName()).setValue(field.get(obj));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public Sync child(String child) {
    return new FirebaseSync().setRef(getRef().child(child));
  }

  @Override
  public Sync sibling(String child) {
    return new FirebaseSync().setRef(getRef().getParent().child(child));
  }
}

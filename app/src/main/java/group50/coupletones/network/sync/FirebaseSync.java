package group50.coupletones.network.sync;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
public class FirebaseSync implements Sync {

  /**
   * The object to sync
   */
  protected Object obj;

  protected DatabaseReference ref;

  protected Map<String, Field> syncFields;

  /**
   * Sets the Sync object to watch a particular object
   *
   * @param obj The object to watch
   * @return Self instance
   */
  @Override
  public FirebaseSync watch(Object obj) {
    this.obj = obj;
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
   */
  protected void cacheFields() {
    verifyRefAndObjSet();
    syncFields = new HashMap<>();

    Field[] fields = obj.getClass().getFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(Syncable.class)) {
        // Force accessibility
        field.setAccessible(true);
        syncFields.put(field.getName(), field);
      }
    }
  }

  /**
   * Binds a Firebase listener that automatically updates the fields.
   * upon data change.
   */
  public Sync subscribeAll() {
    verifyRefAndObjSet();
    if (syncFields == null)
      cacheFields();

    // Add a listener for each field
    for (String fieldName : syncFields.keySet()) {
      subscribe(fieldName);
    }

    return this;
  }

  /**
   * Subscribes a field in the class to receive updates from the database.
   *
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Sync subscribe(String fieldName) {
    verifyRefAndObjSet();
    if (syncFields == null)
      cacheFields();

    Field f = syncFields.get(fieldName);
    ref
      .child(f.getName())
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          try {
            f.set(obj, dataSnapshot.getValue());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          throw databaseError.toException();
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
    verifyRefAndObjSet();
    if (syncFields == null)
      cacheFields();

    for (String fieldName : fieldNames) {
      if (syncFields.containsKey(fieldName))
        publish(syncFields.get(fieldName));
      else
        throw new IllegalArgumentException("Field name: " + fieldName + " does not have @Sycnable annotation.");
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
    verifyRefAndObjSet();

    try {
      ref.child(field.getName()).setValue(field.get(obj));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }
}

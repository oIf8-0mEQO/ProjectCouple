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
  protected final Object obj;

  protected final DatabaseReference ref;

  protected Map<String, Field> syncFields;

  public FirebaseSync(Object obj, DatabaseReference ref) {
    this.obj = obj;
    this.ref = ref;
  }

  /**
   * Cache all fields of the given object that requires syncing.
   */
  protected void cacheFields() {
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
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Sync update(String fieldName) {
    return update(syncFields.get(fieldName));
  }

  /**
   * Attempts to sync a specific field to the server
   *
   * @param field The field instance
   * @return Self instance
   */
  protected Sync update(Field field) {
    try {
      ref.child(field.getName()).setValue(field.get(obj));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }
}

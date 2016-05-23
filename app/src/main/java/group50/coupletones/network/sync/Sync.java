package group50.coupletones.network.sync;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * An object that handles Firebase real time database syncing for objects.
 *
 * @author Henry Mao
 * @since 5/22/16
 */
//TODO: Unit test
public class Sync {

  /**
   * The object to sync
   */
  protected final Object obj;

  protected final DatabaseReference ref;

  protected final Set<Field> syncFields = new HashSet<>();

  public Sync(Object obj, DatabaseReference ref) {
    this.obj = obj;
    this.ref = ref;

    cacheFields();
    bindListener();
  }

  /**
   * Cache all fields of the given object that requires syncing.
   */
  protected void cacheFields() {
    Field[] fields = obj.getClass().getFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(Syncable.class)) {
        // Force accessibility
        field.setAccessible(true);
        syncFields.add(field);
      }
    }
  }

  /**
   * Binds a Firebase listener that automatically updates the fields
   * upon data change.
   */
  protected void bindListener() {
    // Add a listener for each field
    for (Field f : syncFields) {
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
    }
  }
}

package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import group50.coupletones.util.properties.Properties;
import group50.coupletones.util.properties.Property;
import rx.subjects.Subject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Observes fields and syncs fields with Firebase
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

  /**
   * Recursively stores into Firebase
   * @param db The database
   * @param value The value to store
   */
  private static void send(DatabaseReference db, Object value) {
    Log.v("FirebaseSync", "Send " + db.getKey() + " with " + value);
    db.setValue(value);
/*
    if (value instanceof String ||
      value instanceof Integer ||
      value instanceof Double ||
      value instanceof Long) {
      // Basic types
      db.setValue(value);
    } else if (value instanceof PropertiesProvider) {
      // Store class information
      db.child("class").setValue(value.getClass().getName());
      // Sync all properties of object
      Collection<Property<?>> all = ((PropertiesProvider) value).getProperties().all();
      for (Property<?> childProperty : all) {
        send(db.child(childProperty.name()), childProperty.get());
      }
    } else if (value instanceof Collection) {
      // Collection type syncing
      int index = 0;
      for (Object item : (Collection) value) {
        // Sync all property of nested object
        send(db.child("" + index), item);
        index++;
      }
    } else if (value == null) {
      db.removeValue();
    } else {
      throw new IllegalArgumentException("Collection contain " + db.getKey() + " [" + value + "] which is not a syncable.");
    }*/
  }

  private static Object receive(DataSnapshot snapshot) {
    Log.v("FirebaseSync", "Receive " + snapshot.getKey());
/*
    if (snapshot.getValue() instanceof Map) {
      // Custom properties provider class
      if (snapshot.hasChild("class")) {
        String className = (String) snapshot.child("class").getValue();
        try {
          PropertiesProvider newObject = (PropertiesProvider) Class.forName(className).newInstance();
          for (Property prop : newObject.getProperties().all()) {
            if (snapshot.hasChild(prop.name())) {
              prop.set(snapshot.child(prop.name()));
            }
          }
          return newObject;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    if (snapshot.getValue() instanceof List) {
      // This is a collection. Rebuild the list from scratch
      List<Object> list = new LinkedList<>();
      int i = 0;

      while (snapshot.child("" + i).exists()) {
        DataSnapshot child = snapshot.child("" + i);
        list.add(child);
        i++;
      }

      return list;
    }*/

    return snapshot.getValue();
  }

  protected void verify() {
    if (ref == null) {
      throw new IllegalStateException("Database reference is null.");
    }
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
            Subject observable = ((Property) property).observable();
            if (property.getIndicator() != null) {
              observable.onNext(dataSnapshot.getValue(property.getIndicator()));
            } else {
              observable.onNext(receive(dataSnapshot));
            }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
          }
        })
    );

    // When the property changes, we want to tell update Firebase about it

    // Datatype syncing
    property
      .observable()
      .distinct()
      .subscribe(value -> send(ref.child(property.name()), value));

    return this;
  }

  @Override
  public FirebaseSync parent() {
    return new FirebaseSync(ref.getParent());
  }

  @Override
  public FirebaseSync child(String name) {
    return new FirebaseSync(ref.child(name));
  }

  public DatabaseReference getRef() {
    return ref;
  }
}

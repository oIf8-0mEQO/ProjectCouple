package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import group50.coupletones.util.observer.ConcreteProperties;
import group50.coupletones.util.observer.Properties;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Observes fields and syncs fields with Firebase
 * @author Henry Mao
 */
public class FirebaseSync implements Sync {

  protected DatabaseReference ref;

  public FirebaseSync() {
  }

  public FirebaseSync(Properties parent, Object obj) {
    super(parent, obj);
  }

  /**
   * Cache all fields of the given object that requires syncing.
   * Automatically generates observables for every single field.
   */
  protected void build() {
    verify();
    if (observables == null) {
      observables = new HashMap<>();
      Class<?> scanClass = obj.getClass();
      scanClass(scanClass);
      buildSubjects(scanClass);
    }
  }

  protected void buildSubjects(Class<?> scanClass) {
    Collection<Field> fields = fieldCache.get(scanClass).values();
    for (Field field : fields) {
      // Creates an observable that listens to Firebase data change.
      try {
        String name = field.getName();
        Object defaultObj = field.get(obj);
        observables.put(name, BehaviorSubject.create(defaultObj));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  protected void scanClass(Class<?> scanClass) {
    if (!fieldCache.containsKey(scanClass)) {
      Field[] fields = scanClass.getDeclaredFields();
      HashMap<String, Field> fieldMap = new HashMap<>();
      fieldCache.put(scanClass, fieldMap);

      for (Field field : fields) {
        if (field.isAnnotationPresent(Watch.class)) {
          // Force accessibility
          field.setAccessible(true);
          String name = field.getName();
          fieldMap.put(name, field);
        }
      }
    }
  }

  /**
   * Gets the observable associated with this field.
   *
   * @param name The name of the field in this class.
   * @return An observable object.
   */
  @Override
  public Observable<?> getObservable(String name) {
    build();
    if (observables.containsKey(name)) {
      return observables.get(name);
    } else {
      return Observable.empty();
    }
  }

  /**
   * Binds a Firebase listener that automatically updates the fields.
   * upon data change.
   */
  public Properties subscribeAll() {
    build();

    // Add a listener for each field
    for (String fieldName : fieldCache.get(obj.getClass()).keySet()) {
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
  public Properties subscribe(String fieldName) {
    build();
    Observable<?> observable = getObservable(fieldName);
    Field field = fieldCache.get(obj.getClass()).get(fieldName);
    observable.subscribe(change -> {
      try {
        Log.v("FirebaseSync", "Receieved " + fieldName + " with " + change);
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
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Properties publish(String fieldName) {
    build();

    Map<String, Field> fieldMap = fieldCache.get(obj.getClass());
    if (fieldMap.containsKey(fieldName)) {
      publish(fieldMap.get(fieldName));
    } else {
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
  protected Properties publish(Field field) {
    build();
    return this;
  }

  /**
   * Publishes all fields to the database.
   *
   * @return Self instance
   */
  @Override
  public Properties publishAll() {
    build();

    // Add a listener for each field
    for (String fieldName : fieldCache.get(obj.getClass()).keySet()) {
      publish(fieldName);
    }

    return this;
  }

  @Override
  public ConcreteProperties set(Object obj) {
    return new FirebaseSync(parent, obj).setRef(ref);
  }

  public DatabaseReference getRef() {
    return ref;
  }

  /**
   * Sets the Properties object to refer a particular database
   *
   * @param ref The database use
   * @return Self instance
   */
  public ConcreteProperties setRef(DatabaseReference ref) {
    this.ref = ref;
    return this;
  }

  protected void verify() {
    if (ref == null) {
      throw new IllegalStateException("Reference null.");
    }

    super.verify();
  }

  @Override
  protected void buildSubjects(Class<?> scanClass) {
    super.buildSubjects(scanClass);
    listenFirebase();
  }

  @Override
  protected Properties publish(Field field) {
    super.publish(field);

    try {
      Log.v("FirebaseSync", "Publishing: " + field.getName() + " with: " + field.get(obj));
      ref.child(field.getName()).setValue(field.get(obj));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public Properties child(String child) {
    return new FirebaseSync(this, obj).setRef(getRef().child(child));
  }

  @Override
  public Properties parent() {
    return ((FirebaseSync) parent.set(obj)).setRef(getRef().getParent());
  }

  /**
   * Sets the Properties object to set a particular object
   *
   * @param obj The object to set
   * @return A new instance that watches the givne obje
   */
  @Override
  public ConcreteProperties set(Object obj) {
    return new ConcreteProperties(parent, obj);
  }

  @Override
  public Properties child(String child) {
    return new ConcreteProperties(this, obj);
  }

  @Override
  public Properties parent() {
    return parent;
  }

  //TODO: Need to dispose listeners
  private void listenFirebase() {
    for (Map.Entry<String, BehaviorSubject<?>> entry : observables.entrySet()) {
      String name = entry.getKey();
      BehaviorSubject subject = entry.getValue();

      ref.child(name)
        .addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            subject.onNext(dataSnapshot.getValue());
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
          }
        });
    }
  }
}

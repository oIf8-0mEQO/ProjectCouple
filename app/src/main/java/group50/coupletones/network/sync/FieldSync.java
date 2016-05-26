package group50.coupletones.network.sync;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that handles Firebase real time database syncing for objects.
 * @author Henry Mao
 * @since 5/22/16
 */
//TODO: Unit test
//TODO: Refactor to field publisher, generic. Returns an interface for publishing
public class FieldSync implements Sync {
  /**
   * A static cache of all classes and their annotated fields.
   * Reflection is slow, and caching it is generally faster.
   */
  protected static final Map<Class<?>, Map<String, Field>> syncFields = new HashMap<>();

  /**
   * The object to sync
   */
  protected final Object obj;

  protected DatabaseReference ref;

  protected Map<String, BehaviorSubject<?>> observables;

  /**
   * Creates an unusable Sync.
   */
  public FieldSync() {
    this(null);
  }

  public FieldSync(Object obj) {
    this.obj = obj;
  }

  /**
   * Sets the Sync object to watch a particular object
   * @param obj The object to watch
   * @return A new instance that watches the givne obje
   */
  @Override
  public FieldSync watch(Object obj) {
    return new FieldSync(obj).setRef(ref);
  }

  @Override
  public DatabaseReference getRef() {
    return ref;
  }

  /**
   * Sets the Sync object to refer a particular database
   * @param ref The database use
   * @return Self instance
   */
  @Override
  public FieldSync setRef(DatabaseReference ref) {
    this.ref = ref;
    return this;
  }

  protected void verifyRefAndObjSet() {
    if (obj == null || ref == null) {
      throw new IllegalStateException("Object or reference null. Unable to sync.");
    }
  }

  /**
   * Cache all fields of the given object that requires syncing.
   * Automatically generates observables for every single field.
   */
  protected void build() {
    verifyRefAndObjSet();
    if (observables == null) {
      observables = new HashMap<>();
      Class<?> scanClass = obj.getClass();
      scanClass(scanClass);
      buildSubjects(scanClass);
    }
  }

  protected void buildSubjects(Class<?> scanClass) {
    Collection<Field> fields = syncFields.get(scanClass).values();
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
    if (!syncFields.containsKey(scanClass)) {
      Field[] fields = scanClass.getDeclaredFields();
      HashMap<String, Field> fieldMap = new HashMap<>();
      syncFields.put(scanClass, fieldMap);

      for (Field field : fields) {
        if (field.isAnnotationPresent(Syncable.class)) {
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
  public Sync subscribeAll() {
    build();

    // Add a listener for each field
    for (String fieldName : syncFields.get(obj.getClass()).keySet()) {
      subscribe(fieldName);
    }

    return this;
  }

  /**
   * Subscribes a field in the class to receive updates from the database automatically.
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Sync subscribe(String fieldName) {
    build();
    Observable<?> observable = getObservable(fieldName);
    Field field = syncFields.get(obj.getClass()).get(fieldName);
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
   * @param fieldName The name of the field
   * @return Self instance
   */
  @Override
  public Sync publish(String fieldName) {
    build();

    Map<String, Field> fieldMap = syncFields.get(obj.getClass());
    if (fieldMap.containsKey(fieldName)) {
      publish(fieldMap.get(fieldName));
    } else {
      throw new IllegalArgumentException("Field name: " + fieldName + " does not have @Sycnable annotation.");
    }

    return this;
  }

  /**
   * Publishes all fields to the database.
   * @return Self instance
   */
  @Override
  public Sync publishAll() {
    build();

    // Add a listener for each field
    for (String fieldName : syncFields.get(obj.getClass()).keySet()) {
      publish(fieldName);
    }

    return this;
  }

  /**
   * Attempts to sync a specific field to the server
   * @param field The field instance
   * @return Self instance
   */
  protected Sync publish(Field field) {
    build();

    try {
      Log.v("FirebaseSync", "Publishing: " + field.getName() + " with: " + field.get(obj));
      ref.child(field.getName()).setValue(field.get(obj));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public Sync child(String child) {
    return new FieldSync(obj).setRef(getRef().child(child));
  }

  @Override
  public Sync sibling(String child) {
    return new FieldSync(obj).setRef(getRef().getParent().child(child));
  }
}

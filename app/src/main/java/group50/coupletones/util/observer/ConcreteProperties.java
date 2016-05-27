package group50.coupletones.util.observer;

import group50.coupletones.util.function.Consumer;
import group50.coupletones.util.function.Supplier;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * An object that watches an object's fields, notifies observers when a field updates
 * and updates them when publish is called.
 *
 * @author Henry Mao
 * @since 5/22/16
 */
//TODO: Unit test
public class ConcreteProperties implements Properties {

  /**
   * The object to observe.
   * Immutable
   */
  protected final Object obj;

  /**
   * Map of bindings the object watched by this property has.
   */
  protected Map<String, Property<?>> bindings;

  /**
   * Creates an unusable Properties.
   */
  public ConcreteProperties() {
    this(null);
  }

  public ConcreteProperties(Object obj) {
    this.obj = obj;
  }

  protected void verify() {
    if (obj == null) {
      throw new IllegalStateException("Object being bound is null. Unable to sync.");
    }
  }

  @Override
  public Properties set(Object obj) {
    return new ConcreteProperties(obj);
  }

  @Override
  public <T> Property<T> property(String name, Class<T> type) {
    verify();
    return bindings.containsKey(name) ? (Property<T>) bindings.get(name) : new ConcreteProperty<>(name);
  }


  @Override
  public Collection<Property<?>> all() {
    return bindings.values();
  }

  public class ConcreteProperty<T> implements Property<T> {

    private final String name;
    private BehaviorSubject<T> observable;
    private Consumer<T> setter;
    private Supplier<T> getter;

    public ConcreteProperty(String name) {
      this.name = name;
    }

    @Override
    public String name() {
      return name;
    }

    @Override
    public Property<T> setter(Consumer<T> setter) {
      this.setter = setter;
      return this;
    }

    @Override
    public Property<T> getter(Supplier<T> getter) {
      this.getter = getter;
      return this;
    }

    @Override
    public Properties bind() {
      // If setter or getters are not set, create default property bindings via reflection.
      if (getter == null) {
        getter = () -> {
          try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(obj);
          } catch (Exception e) {
            throw new IllegalArgumentException("Default binding for " + name + " cannot be read.");
          }
        };
      }

      if (setter == null) {
        setter = value -> {
          try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
          } catch (Exception e) {
            throw new IllegalArgumentException("Default binding for " + name + " cannot be written.");
          }
        };
      }

      observable = BehaviorSubject.create(getter.get());
      ConcreteProperties.this.bindings.put(name, this);
      return ConcreteProperties.this;
    }

    @Override
    public T get() {
      return getter.get();
    }

    @Override
    public void set(T value) {
      setter.accept(value);
    }

    @Override
    public Subject<T, T> observable() {
      return observable;
    }
  }
}

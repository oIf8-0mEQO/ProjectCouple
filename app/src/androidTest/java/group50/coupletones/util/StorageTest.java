package group50.coupletones.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Calvin
 * @since 4/28/2016
 */
@RunWith(AndroidJUnit4.class)
public class StorageTest extends ActivityInstrumentationTestCase2<MainActivity> {
  private Storage storage;

  public StorageTest() {
    super(MainActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());

    storage = new Storage(getActivity().getSharedPreferences("test", Context.MODE_PRIVATE));
  }

  @Override
  public void tearDown() throws Exception {
    // Clear preferences
    getActivity().getSharedPreferences("test", Context.MODE_PRIVATE).edit().clear().apply();
    super.tearDown();
  }

  @Test
  public void testContain() {
    storage.setInt("mac", 0);
    assertThat(storage.contains("mac")).isTrue();
    assertThat(storage.contains("ABC")).isFalse();
    storage.setString("mac", "DEF");
    assertThat(storage.contains("mac")).isTrue();
  }

  @Test
  public void testTypeInt() {
    storage.setInt("mac", 85);
    assertThat(storage.getInt("mac")).isEqualTo(85);
    assertThat(storage.getInt("ABC")).isEqualTo(0);
    storage.setInt("def", 67);
    assertThat(storage.getInt("def")).isEqualTo(67);
  }


  @Test
  public void testTypeFloat() {
    storage.setFloat("mac", 85f);
    assertThat(storage.getFloat("mac")).isEqualTo(85f);
    storage.setFloat("def", 33f);
    assertThat(storage.getFloat("def")).isEqualTo(33f);
    assertThat(storage.getFloat("ABC")).isEqualTo(0f);
  }

  @Test
  public void testTypeString() {
    storage.setString("mac", "mac2");
    assertThat(storage.getString("mac")).isEqualTo("mac2");

    storage.setString("def", "");
    assertThat(storage.getString("def")).isEqualTo("");

    storage.setString("app", "   ");
    assertThat(storage.getString("app")).isEqualTo("   ");

    assertThat(storage.getString("ABC")).isEqualTo(null);
  }

  @Test
  public void testTypeBoolean() {
    storage.setBoolean("mac", true);
    assertThat(storage.getBoolean("mac")).isEqualTo(true);

    storage.setBoolean("mac1", false);
    assertThat(storage.getBoolean("mac1")).isEqualTo(false);

    assertThat(storage.getBoolean("ABC")).isEqualTo(false);
  }

  @Test
  public void testCollection() throws Exception {
    LinkedList<StorableObject> mockList = new LinkedList<>();
    mockList.add(new StorableObject("test1"));
    mockList.add(new StorableObject("test2"));
    mockList.add(new StorableObject("test3"));

    storage.setCollection("list", mockList);

    List<StorableObject> retrievedList = storage.getCollection("list", StorableObject::new);

    assertThat(retrievedList.size()).isEqualTo(mockList.size());

    for (StorableObject obj : mockList)
      assertThat(retrievedList.contains(obj)).isTrue();
  }


  @Test
  public void testEmptyCollection() throws Exception {
    LinkedList<StorableObject> mockList = new LinkedList<>();
    storage.setCollection("list", mockList);
    List<StorableObject> retrievedList = storage.getCollection("list", StorableObject::new);
    assertThat(retrievedList.size()).isEqualTo(mockList.size());
  }

  @Test(expected = RuntimeException.class)
  public void testCollectionInvalidName() throws Exception {
    LinkedList<StorableObject> mockList = new LinkedList<>();
    mockList.add(new StorableObject("test1"));
    mockList.add(new StorableObject("test2"));
    mockList.add(new StorableObject("test3"));

    storage.setCollection("list_", mockList);
  }

  @Test
  public void testDelete() {
    storage.setBoolean("mac", true);
    assertThat(storage.getBoolean("mac")).isTrue();
    storage.delete("mac");
    assertThat(storage.contains("mac")).isFalse();
    assertThat(storage.getBoolean("mac")).isFalse();

    storage.setString("mac1", "ight");
    assertThat(storage.getString("mac1")).isEqualTo("ight");
    storage.delete("mac1");
    assertThat(storage.getString("mac1")).isEqualTo(null);
    assertThat(storage.contains("mac1")).isFalse();

    storage.delete("mac3");
    assertThat(storage.getInt("mac3")).isEqualTo(0);
  }

  class StorableObject implements Storable {
    public String val;

    public StorableObject() {
    }

    public StorableObject(String val) {
      this.val = val;
    }

    @Override
    public void save(Storage storage) {
      storage.setString("test", val);
    }

    @Override
    public void load(Storage storage) {
      val = storage.getString("test");
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof StorableObject)
        return val.equals(((StorableObject) o).val);
      return false;
    }

    @Override
    public int hashCode() {
      return val.hashCode();
    }
  }
}
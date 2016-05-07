package group50.coupletones.util;

import android.content.SharedPreferences;
import android.support.test.runner.AndroidJUnit4;
import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Calvin on 4/28/2016.
 */
@RunWith(AndroidJUnit4.class)
public class StorageTest {
  private Storage storage;

  private SharedPreferences mockPreference;

  private HashMap<String, Object> cache;

  @Before
  public void setUp() throws Exception {
    mockPreference = mock(SharedPreferences.class);

    // Mock the functions used
    SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
    when(mockPreference.edit()).thenReturn(editor);

    // Mock set methods
    when(editor.putInt(any(), any())).then(this::cacheSet);
    when(editor.putBoolean(any(), any())).then(this::cacheSet);
    when(editor.putFloat(any(), any())).then(this::cacheSet);
    when(editor.putString(any(), any())).then(this::cacheSet);

    // Mck get methods
    when(mockPreference.contains(any())).then(inv -> cache.containsKey(inv));
    when(mockPreference.getInt(any(), any())).then(this::cacheGet);
    when(mockPreference.getBoolean(any(), any())).then(this::cacheGet);
    when(mockPreference.getFloat(any(), any())).then(this::cacheGet);
    when(mockPreference.getString(any(), any())).then(this::cacheGet);

    storage = new Storage(mockPreference);
  }

  /**
   * Sets the cache
   *
   * @param inv The mock invocation
   */
  private Object cacheSet(InvocationOnMock inv) {
    cache.put((String) inv.getArguments()[0], inv.getArguments()[1]);
    return null;
  }


  /**
   * Gets the cache
   *
   * @param inv The mock invocation
   */
  private Object cacheGet(InvocationOnMock inv) {
    return cache.get(inv.getArguments()[0]);
  }

  @Test
  public void testContain() {
    storage.setInt("mac", 0);
    assertTrue(storage.contains("mac"));

    assertFalse(storage.contains("ABC"));

    storage.setString("mac", "DEF");
    assertThat(storage.contains("mac")).isEqualTo(true);
  }


  @Test
  public void testTypeInt() {
    storage.setInt("mac", 85);
    assertThat(storage.getInt("mac")).isEqualTo(85);
    System.out.print(storage.getInt("mac"));

    assertThat(storage.getInt("ABC")).isEqualTo(0);

    storage.setInt("def", 67);
    assertThat(storage.getInt("def")).isEqualTo(67);
    System.out.print(storage.getInt("def"));

  }


  @Test
  public void testTypeFloat() {
    storage.setFloat("mac", 85f);
    assertThat(storage.getFloat("mac")).isEqualTo(85f);
    System.out.print(storage.getFloat("mac"));

    storage.setFloat("def", 33f);
    assertThat(storage.getFloat("def")).isEqualTo(33f);
    System.out.print(storage.getFloat("def"));

    assertThat(storage.getFloat("ABC")).isEqualTo(0f);
  }

  @Test
  public void testTypeString() {
    storage.setString("mac", "mac2");
    assertThat(storage.getString("mac")).isEqualTo("mac2");
    System.out.print(storage.getString("mac"));

    storage.setString("def", "");
    assertThat(storage.getString("def")).isEqualTo("");
    System.out.print(storage.getString("def"));

    storage.setString("app", "   ");
    assertThat(storage.getString("app")).isEqualTo("   ");
    System.out.print(storage.getString("app"));

    assertThat(storage.getString("ABC")).isEqualTo(null);
  }

  @Test
  public void testTypeBoolean() {
    storage.setBoolean("mac", true);
    assertThat(storage.getBoolean("mac")).isEqualTo(true);
    System.out.print(storage.getBoolean("mac"));

    storage.setBoolean("mac1", false);
    assertThat(storage.getBoolean("mac1")).isEqualTo(false);
    System.out.print(storage.getBoolean("mac1"));

    assertThat(storage.getBoolean("ABC")).isEqualTo(false);
  }

  @Test
  public void testCollection() throws Exception {

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
    }

    LinkedList<StorableObject> mockList = new LinkedList<>();
    mockList.add(new StorableObject("test1"));
    mockList.add(new StorableObject("test2"));
    mockList.add(new StorableObject("test3"));

    storage.setCollection("list", mockList);

    List<StorableObject> retrievedList = storage.getCollection("list", StorableObject.class);

    assertThat(retrievedList.size()).isEqualTo(mockList.size());
    assertThat(retrievedList).isEqualTo(mockList);
  }
}
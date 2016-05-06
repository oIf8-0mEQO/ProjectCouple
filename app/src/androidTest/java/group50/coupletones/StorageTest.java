package group50.coupletones;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import group50.coupletones.util.storage.Storage;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Calvin on 4/28/2016.
 */

@RunWith(AndroidJUnit4.class)
public class StorageTest {
  Storage storage;


  @Test
  public void setUp() throws Exception {
    Activity activity = mock(Activity.class);
    Storage storage = new Storage(activity);
  }


    public void testContain() {

    storage.write("mac", 0);
      assertTrue(storage.contains("mac"));
  }


  @Test
  public void testTypeInt() {
    storage.write("mac", 85);
    assertEquals(storage.readInt("mac"), 85);
    System.out.print(storage.readInt("mac"));
  }


  @Test
  public void testTypeFloat() {
    storage.write("mac", 85f);
    assertThat(storage.readFloat("mac")).isEqualTo(85f);
    System.out.print(storage.readFloat("mac"));
  }

}
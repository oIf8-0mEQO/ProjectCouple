package group50.coupletones.util;

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

        storage.setInt("mac", 0);
        assertTrue(storage.contains("mac"));
    }


    @Test
    public void testTypeInt() {
        storage.setInt("mac", 85);
        assertEquals(storage.getInt("mac"), 85);
        System.out.print(storage.getInt("mac"));
    }


    @Test
    public void testTypeFloat() {
        storage.setFloat("mac", 85f);
        assertThat(storage.getFloat("mac")).isEqualTo(85f);
        System.out.print(storage.getFloat("mac"));
    }

    @Test
    public void testTypeString() {
        storage.setFloat("mac", 85f);
        assertThat(storage.getFloat("mac")).isEqualTo(85f);
        System.out.print(storage.getFloat("mac"));

        @Test
        public void testTypeBoolean() {
            storage.setFloat("mac", 85f);
            assertThat(storage.getFloat("mac")).isEqualTo(85f);
            System.out.print(storage.getFloat("mac"));




        }

    }
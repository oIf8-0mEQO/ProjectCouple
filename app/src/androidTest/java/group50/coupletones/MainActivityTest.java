package group50.coupletones;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.PartnersLocationsFragment;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Henry Mao
 * @since 4/25/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private MainActivity activity;

  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();

    // Injecting the Instrumentation instance is required
    // for your test to run with AndroidJUnitRunner.
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  @Test
  public void testDefaultTab() throws Exception {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be only one fragment active upon launch
    assertThat(fragments).hasSize(1);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(0)).isOfAnyClassIn(PartnersLocationsFragment.class);
  }
}

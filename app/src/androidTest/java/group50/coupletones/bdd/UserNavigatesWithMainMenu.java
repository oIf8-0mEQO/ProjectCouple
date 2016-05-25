package group50.coupletones.bdd;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.SettingsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.partnerslocations.PartnersLocationsFragment;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Henry Mao
 * @since 4/25/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserNavigatesWithMainMenu extends ActivityInstrumentationTestCase2<MainActivity> {

  private MainActivity activity;

  // The default tab expected
  private Class<PartnersLocationsFragment> defaultTabClass = PartnersLocationsFragment.class;

  public UserNavigatesWithMainMenu() {
    super(MainActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();

    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build());

    // Injecting the Instrumentation instance is required
    // for your test to run with AndroidJUnitRunner.
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  /**
   * Test the default tab when the app launches
   *
   * @throws Exception
   */
  @Test
  public void testDefaultTab() throws Exception {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be only one fragment active upon launch
    assertThat(fragments).hasSize(1);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(0)).isOfAnyClassIn(defaultTabClass);
  }

  private void givenThatIAmLoggedIn() {
    // Stub getLocalUser method
    when(CoupleTones.global().app().getLocalUser())
      .thenReturn(mock(LocalUser.class));
  }

  private void whenITapOnPartnersLocation() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void whenITapOnFavoriteLocation() {
    onView(withId(R.id.favorite_locations)).perform(click());
  }

  private void whenITapOnSettings() {
    onView(withId(R.id.settings)).perform(click());
  }

  private void thenTheAppDisplaysPartnersLocation() {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be one fragment since the default is already set to this fragment
    assertThat(fragments).hasSize(1);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(0)).isOfAnyClassIn(PartnersLocationsFragment.class);
  }

  private void thenTheAppDisplaysFavoriteLocation() {
    activity.runOnUiThread(() -> {
      FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

      List<Fragment> fragments = supportFragmentManager.getFragments();
      // There should be only one fragment active upon launch
      assertThat(fragments).hasSize(2);
      // Test to make sure the fragment is a PartnersLocationsFragment
      assertThat(fragments.get(1)).isOfAnyClassIn(FavoriteLocationsFragment.class);
    });
  }

  private void thenTheAppDisplaysSettings() {
    activity.runOnUiThread(() -> {
      FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

      List<Fragment> fragments = supportFragmentManager.getFragments();
      // There should be only one fragment active upon launch
      assertThat(fragments).hasSize(2);
      // Test to make sure the fragment is a PartnersLocationsFragment
      assertThat(fragments.get(1)).isOfAnyClassIn(SettingsFragment.class);
    });
  }

  /**
   * Test tapping on the first tab
   *
   * @throws Exception
   */
  @Test
  public void userTapsOnPartnersLocation() throws Exception {
    givenThatIAmLoggedIn();
    whenITapOnPartnersLocation();
    thenTheAppDisplaysPartnersLocation();
  }

  /**
   * Test tapping on the second tab
   *
   * @throws Exception
   */
  @Test
  public void userTapsOnFavoriteLocationsTab() throws Exception {
    givenThatIAmLoggedIn();
    whenITapOnFavoriteLocation();
    thenTheAppDisplaysFavoriteLocation();
  }

  /**
   * Test tapping on the third tab
   *
   * @throws Exception
   */
  @Test
  public void userTapsOnSettingsTab() throws Exception {
    givenThatIAmLoggedIn();
    whenITapOnSettings();
    thenTheAppDisplaysSettings();
  }

  /**
   * Test when an invalid tab tap occurs
   *
   * @throws Exception
   */
  @Test
  public void testTabInvalid() throws Exception {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    // Tap invalid tab should cause no change
    activity.onMenuTabSelected(0);

    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be only one fragment active upon launch
    assertThat(fragments).hasSize(1);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(0)).isOfAnyClassIn(defaultTabClass);
  }
}

package group50.coupletones.controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import dagger.Component;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.User;
import group50.coupletones.controller.tab.PartnersLocationsFragment;
import group50.coupletones.di.AppComponent;
import group50.coupletones.di.MockApplicationModule;
import group50.coupletones.di.MockAuthenticatorModule;
import group50.coupletones.di.module.NetworkModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Henry Mao
 * @since 4/25/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private MainActivity activity;

  // The default tab expected
  private Class<PartnersLocationsFragment> defaultTabClass = PartnersLocationsFragment.class;

  public MainActivityTest() {
    super(MainActivity.class);
  }

  //TODO: Add test for changing to settings tab view

  @Before
  public void setUp() throws Exception {
    super.setUp();

    CoupleTones.setComponent(
      DaggerMainActivityTest_TestAppComponent
        .builder()
        .build());

    //TODO: DRY
    // Stub getLocalUser method
    when(CoupleTones.component().app().getLocalUser())
      .thenReturn(new User() {
        @Override
        public String getId() {
          return "mockuser";
        }

        @Override
        public String getName() {
          return "Mock User";
        }

        @Override
        public String getEmail() {
          return "mock@mock.com";
        }
      });

    // Injecting the Instrumentation instance is required
    // for your test to run with AndroidJUnitRunner.
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  /**
   * Test the default tab when the app launches
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

  /**
   * Test tapping on the first tab
   * @throws Exception
   */
  @Test
  public void testTabTap1() throws Exception {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    activity.onMenuTabSelected(R.id.partnerLocations);

    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be one fragment since the default is already set to this fragment
    assertThat(fragments).hasSize(1);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(0)).isOfAnyClassIn(PartnersLocationsFragment.class);
  }

  /**
   * Test tapping on the second tab
   * @throws Exception
   */
  /*
  @Test
  public void testTabTap2() throws Exception {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    activity.onMenuTabSelected(R.id.favoriteLocations);
    List<Fragment> fragments = supportFragmentManager.getFragments();
    // There should be only one fragment active upon launch
    assertThat(fragments).hasSize(2);
    // Test to make sure the fragment is a PartnersLocationsFragment
    assertThat(fragments.get(1)).isOfAnyClassIn(FavoriteLocationsFragment.class);
  }*/

  /**
   * Test when an invalid tab tap occurs
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

  /**
   * The dependency injection component for the entire app using mocks.
   * @author Henry Mao
   * @since 28/4/2016
   */
  @Singleton
  @Component(
    modules = {
      MockAuthenticatorModule.class,
      MockApplicationModule.class,
      NetworkModule.class
    }
  )
  public static interface TestAppComponent extends AppComponent {
  }
}

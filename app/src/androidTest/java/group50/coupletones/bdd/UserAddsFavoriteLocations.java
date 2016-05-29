package group50.coupletones.bdd;


import android.app.Instrumentation;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.LocationClickHandler;
import group50.coupletones.controller.tab.favoritelocations.map.MapFragment;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.MockUserTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * BDD style test for user adds favorite locations story
 *
 * @author Henry Mao
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserAddsFavoriteLocations {
  private CoupleTones app;
  private LocalUser mockUser;
  private List<FavoriteLocation> favLocations = new LinkedList<>();
  private MapFragment mapFragment;
  // Custom launch rule
  @Rule
  public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();
      // Mock DI
      CoupleTones.setGlobal(
        DaggerMockAppComponent
          .builder()
          .mockProximityModule(new MockProximityModule())
          .build()
      );

      app = CoupleTones.global().app();

      // Mock the local user
      mockUser = (LocalUser) new MockUserTestUtil()
        .injectLocalUser()
        .mockFavoriateLocations(() -> Collections.unmodifiableList(favLocations))
        .mockFavoriteLocationsAdd(favLocations::add)
        .get();
    }

    @Override
    protected void afterActivityLaunched() {
      super.afterActivityLaunched();
      mapFragment = (MapFragment) rule.getActivity().getTabs().get(R.id.map);
    }
  };

  private static void allowPermissionsIfNeeded() {
    if (Build.VERSION.SDK_INT >= 21) {
      UiDevice device = UiDevice.getInstance(getInstrumentation());
      UiObject allowPermissions = device.findObject(new UiSelector().text("Allow"));
      if (allowPermissions.exists()) {
        try {
          allowPermissions.click();
        } catch (UiObjectNotFoundException e) {
          Log.e("UserConfiguresSettings", "There is no permissions dialog to interact with ");
        }
      }
    }
  }

  private void givenLocationValidOnGoogleMaps() {
    // Open "Add Favorite Locations" tab
    onView(withId(R.id.favorite_locations)).perform(click());
    onView(withId(R.id.add_favorite_location)).perform(click());
    allowPermissionsIfNeeded();
  }

  private void whenSaveFavoriteLocation(String locationName) {
    // Simulate click on map and dialog
    EditText text = new EditText(rule.getActivity());
    text.setText(locationName);
    // Override the moveMap to prevent it from moving the map UI
    LocationClickHandler.EventOnAccept eventOnAccept = new LocationClickHandler.EventOnAccept(
      CoupleTones.global().app(),
      mapFragment,
      text,
      new LatLng(32.882, -117.233)
    ) {
      @Override
      protected void moveMap(FavoriteLocation clickedLocation) {

      }
    };

    eventOnAccept.onClick(null, 0);
  }

  private void thenLocationWillBeAddedToList(String sampleLocationName) {
    // Then the location will be added to my favorite list
    assertThat(app.getLocalUser().getFavoriteLocations()).hasSize(1);
    assertThat(app.getLocalUser().getFavoriteLocations().get(0).getName()).isEqualTo(sampleLocationName);
    // Go back to the list
    onView(withId(R.id.favorite_locations)).perform(click());
    onView(withId(R.id.favorite_locations_list)).check(matches(hasDescendant(withText(sampleLocationName))));
  }

  @Test
  public void userAddsValidLocation() throws Exception {
    givenLocationValidOnGoogleMaps();
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    String sampleLocationName = "My Home";

    whenSaveFavoriteLocation(sampleLocationName);
    thenLocationWillBeAddedToList(sampleLocationName);

    getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
  }
}

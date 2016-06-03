package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Sharmaine Manalo
 * @since 5/8/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDeletesFavoriteLocation {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private UserTestUtil testUtil;
  private LatLng zoneLatLng = new LatLng(32.882, -117.233);
  private FavoriteLocation zone = new FavoriteLocation("Home", zoneLatLng, 0, VibeTone.getTone().getIndex());
  private List<FavoriteLocation> emptyData;

  @Before
  public void setup() {
    testUtil = new ConcreteUserTestUtil();
    testUtil.injectLocalUser();
    emptyData = new LinkedList<>();
  }

  private void givenUserHasFavoriteLocation() {
    testUtil.mockFavoriteLocations(() -> Collections.singletonList(zone));
    onView(withId(R.id.favorite_locations)).perform(click());
  }

  private void whenUserClicksDeleteOnFavoriteLocation() {
    onView(withId(R.id.delete_location_icon)).check(matches(isDisplayed()));
    onView(withId(R.id.delete_location_icon)).perform(click());
  }

  private void thenThatLocationWillBeRemovedFromList() {
    verify((LocalUser) testUtil.get(), times(1)).removeFavoriteLocation(zone);
  }

  @Test
  public void userHasAFavoriteLocation() {
    givenUserHasFavoriteLocation();
    whenUserClicksDeleteOnFavoriteLocation();
    thenThatLocationWillBeRemovedFromList();
  }

  private void givenUserDoesNotHaveFavoriteLocation() {
    onView(withId(R.id.favorite_locations)).perform(click());
    emptyData = testUtil.get().getFavoriteLocations();
  }

  private void thenTheFavoriteLocationListIsEmpty() {
    assertThat(emptyData.size()).isEqualTo(0);
  }

  private void andThereIsNoDeleteButton() {
    onView(withId(R.id.favorite_locations)).check(matches(isDisplayed()));
  }

  @Test
  public void userDoesNotHaveAFavoriteLocation() {
    givenUserDoesNotHaveFavoriteLocation();
    thenTheFavoriteLocationListIsEmpty();
    andThereIsNoDeleteButton();
  }
}

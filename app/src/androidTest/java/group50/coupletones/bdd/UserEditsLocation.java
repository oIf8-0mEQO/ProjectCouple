package group50.coupletones.bdd;

import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.LocationDragMediator;
import group50.coupletones.controller.tab.favoritelocations.map.MapFragment;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Joseph Cox
 * @since 6/2/2016
 */
public class UserEditsLocation {

  private FavoriteLocation location;
  private UserTestUtil userTestUtil;
  LinkedList<FavoriteLocation> list;
  private LocalUser user;
  private CoupleTones app;

  @Rule
  public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();
      app = CoupleTones.global().app();

      // Mock the local user
      userTestUtil = new ConcreteUserTestUtil();
      user = (LocalUser) userTestUtil
                           .injectLocalUser()
                           .get();
    }
  };

  @Before
  public void setup() {
    list = new LinkedList<>();
    location = new FavoriteLocation("Test", new LatLng(0, 0), 0, 0);
    list.add(location);
    userTestUtil.get().getProperties()
      .property("favoriteLocations")
      .set(list);
  }

  public void givenUserHasAFavoriteLocation() {
    assertThat(list).hasSize(1);
  }

  public void whenUserInputsNewName() throws Throwable {
    onView(withId(R.id.favorite_locations)).perform(click());
    onView(withId(R.id.edit_location_icon)).perform(click());
    onView(withId(R.id.location_name)).perform(click()).perform(typeText("qwerty"));
    Espresso.closeSoftKeyboard();
    onView(withId(R.id.save_changes_button)).perform(click());
  }

  public void thenLocationsNameWillChange() throws Throwable {
    onView(withId(R.id.favorite_locations)).check(matches(isDisplayed()));
    onView(withId(R.id.list_item_name)).check(matches(withText("Testqwerty")));
  }

  @Test
  public void userRenamesLocation() throws Throwable {
    givenUserHasAFavoriteLocation();
    whenUserInputsNewName();
    thenLocationsNameWillChange();
  }

}

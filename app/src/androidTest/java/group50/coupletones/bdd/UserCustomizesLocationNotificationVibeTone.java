package group50.coupletones.bdd;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;

import org.hamcrest.Matcher;
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

/**
 * @author Henry Mao
 * @since 6/2/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserCustomizesLocationNotificationVibeTone {

  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  private List<FavoriteLocation> favoriteLocations = new LinkedList<>();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      testUtil.injectSpyPartner();

      favoriteLocations.add(new FavoriteLocation("Test", new LatLng(0, 0), 0, 0));

      // Make fake favorite locations for partner
      Partner partner = testUtil.getPartner();
      partner
        .getProperties()
        .property("favoriteLocations")
        .set(favoriteLocations);

      super.beforeActivityLaunched();
    }
  };

  private void givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations() {
    onView(withId(R.id.partners_list_button)).perform(click());
  }

  private void whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations() {
    onView(withId(R.id.edit_location_icon)).perform(click());
  }

  private void thenTheUserWillBeTakenToAPageToChooseFromTenVibeTones() {
    // Ensure the user is on the VibeTonesFragment
    onView(withId(R.id.vibetone_recycler_view)).check(matches(isDisplayed()));
  }

  @Test
  public void userCustomizesLocationToneAndVibration() {
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
    whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations();
    thenTheUserWillBeTakenToAPageToChooseFromTenVibeTones();
  }

  private void givenThatTheUserDoesNotTapOnALocationToCustomizeVibeTones() {
    //Do nothing
  }

  private void whenTheUserViewsThePartnersListOfFavoriteLocations() {
    // Need to view the partner's favorite locations
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
  }

  private void thenTheUserWillHaveTheDefaultVibeToneAssigned() {
    List<FavoriteLocation> favoriteLocations = testUtil.getPartner().getFavoriteLocations();
    assertThat(favoriteLocations).hasSize(1);
    assertThat(favoriteLocations.get(0).getVibetone()).isEqualTo(VibeTone.getDefaultTone());
  }

  @Test
  public void userDoesNotCustomizeLocationVibeTone() {
    givenThatTheUserDoesNotTapOnALocationToCustomizeVibeTones();
    whenTheUserViewsThePartnersListOfFavoriteLocations();
    thenTheUserWillHaveTheDefaultVibeToneAssigned();
  }

  public void givenThatTheUserIsOnThePageToCustomizeVibeTones() {
    givenThatTheUserIsOnThePageOfPartnersListOfFavoriteLocations();
    whenTheUserTapsOnOneOfTheirPartnersFavoriteLocations();
  }

  public void whenTheUserTapsOnAVibeTone() throws Throwable {
    Thread.sleep(1000);
    onView(withId(R.id.vibetone_recycler_view)).check(matches(isDisplayed()));
    onView(withId(R.id.vibetone_recycler_view)).perform(
      RecyclerViewActions.actionOnItemAtPosition(1, ListItemViewAction.clickChildViewWithId(R.id.set_button)));
  }

  public void thenThatVibeToneWillBeAssignedToTheLocationBeingEdited() {
    List<FavoriteLocation> favoriteLocations = testUtil.getPartner().getFavoriteLocations();
    assertThat(favoriteLocations).hasSize(1);
    assertThat(favoriteLocations.get(0).getVibetone()).isEqualTo(VibeTone.getTone(1));
  }

  @Test
  public void userSavesLocationToneAndVibration() throws Throwable {
    givenThatTheUserIsOnThePageToCustomizeVibeTones();
    whenTheUserTapsOnAVibeTone();
    thenThatVibeToneWillBeAssignedToTheLocationBeingEdited();
  }

  // Inner class to handle clicking inside a recycler view list item
  public static class ListItemViewAction {
    public static ViewAction clickChildViewWithId(final int id) {
      return new ViewAction() {
        @Override
        public Matcher<View> getConstraints() {
          return null;
        }

        @Override
        public String getDescription() {
          return "";
        }

        @Override
        public void perform(UiController uiController, View view) {
          View v = view.findViewById(id);
          if (v != null) {
            v.performClick();
          }
        }
      };
    }

  }
}



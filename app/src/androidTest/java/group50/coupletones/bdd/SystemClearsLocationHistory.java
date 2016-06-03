package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.TimeUtility;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * @author Sharmaine Manalo
 * @since 5/30/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class SystemClearsLocationHistory {
  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      super.beforeActivityLaunched();
    }
  };
  private TimeUtility timeUtility;
  private List<VisitedLocationEvent> visitedLocations;
  private VisitedLocationEvent visitedLocation;

  @Before
  public void setup() {
    // Simulate a time the favorite location was visited.
    Calendar june1ElevenPM = Calendar.getInstance();
    june1ElevenPM.set(2016, Calendar.JUNE, 1, 11 + 12, 0, 0);
    FavoriteLocation partnerFavorite = new FavoriteLocation(
      "name",
      new LatLng(10, 10),
      0,
      VibeTone.getDefaultTone().getIndex()
    );

    timeUtility = CoupleTones.global().timeUtility();

    visitedLocation = new VisitedLocationEvent(partnerFavorite, june1ElevenPM.getTime(), june1ElevenPM.getTime());
    visitedLocations = new LinkedList<>();
    visitedLocations.add(visitedLocation);
  }

  /**
   * Scenario 1: User's views the partner's visited before 3AM
   * Given that the user has a partner and views the partner's visited locations list before 3AM
   * When the user views his/her partner's visited locations
   * Then the app will display the list of partner's visited locations since the last 3AM
   */
  private void givenThatItIsBefore3AM() throws Throwable {
    // Stub current time
    Calendar june2TwelveAM = Calendar.getInstance();
    june2TwelveAM.set(2016, Calendar.JUNE, 2, 0, 0, 0);
    doReturn(june2TwelveAM.getTimeInMillis()).when(timeUtility).systemTime();
  }

  private void givenThatItIsAfter3AM() throws Throwable {
    // Stub current time
    Calendar june2FourAM = Calendar.getInstance();
    june2FourAM.set(2016, Calendar.JUNE, 2, 4, 0, 0);
    doReturn(june2FourAM.getTimeInMillis()).when(timeUtility).systemTime();
  }

  private void givenThatUserHasPartner() throws Throwable {
    // User has partner
    rule.runOnUiThread(() -> {

      testUtil
        .mockProperty("name", "Henry")
        .mockProperty("email", "henry@email.com");

      testUtil.injectUserWithId("sharmaine", user -> {
        user.getProperties().property("name").set("Sharmaine");
        user.getProperties().property("email").set("sharmaine@email.com");
        user.getProperties().property("visitedLocations").set(visitedLocations);
        return user;
      });

      testUtil.injectPartner("sharmaine");
    });

    assertThat(testUtil.getPartner()).isNotNull();
  }

  private void whenUserViewsPartnersVisitedLocations() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenAppDisplaysListOfPartnersVisitedLocationsSinceLast3AM() {
    assertThat(testUtil.getPartner().getVisitedLocations()).hasSize(1);
    assertThat(testUtil.getPartner().getVisitedLocations()).contains(visitedLocation);

    onView(withId(R.id.list_item_name)).check(matches(isDisplayed()));
  }

  @Test
  public void userViewsPartnersVisitedLocationsBefore3AM() throws Throwable {
    givenThatItIsBefore3AM();
    givenThatUserHasPartner();
    whenUserViewsPartnersVisitedLocations();
    thenAppDisplaysListOfPartnersVisitedLocationsSinceLast3AM();
  }

  private void thenAppDisplaysListOfPartnersVisitedLocationsAfter3AM() {
    assertThat(testUtil.getPartner().getVisitedLocations()).hasSize(0);

    onView(withId(R.id.list_item_name)).check(doesNotExist());
  }

  @Test
  public void userViewsPartnersVisitedLocationsAfter3AM() throws Throwable {
    givenThatItIsAfter3AM();
    givenThatUserHasPartner();
    whenUserViewsPartnersVisitedLocations();
    thenAppDisplaysListOfPartnersVisitedLocationsAfter3AM();
  }
}

package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.properties.Property;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
public class UserSeesPartnerVisitedLocations {

  private FavoriteLocation home;
  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      super.beforeActivityLaunched();
    }
  };

  private void givenUserHasAPartner() throws Throwable {
    rule.runOnUiThread(() -> testUtil.injectSpyPartner());
  }

  private void whenThePartnerVisitsAZone() throws Throwable {
    home = new FavoriteLocation("Home", new LatLng(0, 0), 0, null);
    VisitedLocationEvent evt = new VisitedLocationEvent(home, new Date(), new Date());
    Partner partner = testUtil.getPartner();
    Property<Object> prop = partner
      .getProperties()
      .property("visitedLocations");
    prop.set(Collections.singletonList(evt));
    rule.runOnUiThread(() -> prop.update());
  }

  private void whenThePartnerDoesNotVisitsAZone() {
  }

  private void andTheUserNavigatesToTheHomePage() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenTheUserWillSeeAllZonesPartnerHas() {
    onView(withId(R.id.list_item_name)).check(matches(isDisplayed()));
    onView(withId(R.id.list_item_name)).check(matches(withText("Home")));
  }

  private void thenTheUserWillNotSeeAnyAdditionalZones() {
    onView(withId(R.id.list_item_name)).check(doesNotExist());
  }

  @Test
  public void userPartnerEntersLocation() throws Throwable {
    givenUserHasAPartner();
    whenThePartnerVisitsAZone();
    andTheUserNavigatesToTheHomePage();
    thenTheUserWillSeeAllZonesPartnerHas();
  }

  @Test
  public void userPartnerDoesNotEntersLocation() throws Throwable {
    givenUserHasAPartner();
    whenThePartnerDoesNotVisitsAZone();
    andTheUserNavigatesToTheHomePage();
    thenTheUserWillNotSeeAnyAdditionalZones();
  }
}

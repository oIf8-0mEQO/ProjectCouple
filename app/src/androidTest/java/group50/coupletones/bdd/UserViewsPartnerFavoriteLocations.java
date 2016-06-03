package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.properties.Property;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doAnswer;

/**
 * @author Joseph
 * @since 5/26/16
 */
public class UserViewsPartnerFavoriteLocations {

  @Rule
  public ActivityTestRule<MainActivity> testRule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      FavoriteLocation partnerFavorite = new FavoriteLocation("name", new LatLng(10, 10), 0, VibeTone.getTone());
      List<FavoriteLocation> locationList = new LinkedList<>();
      locationList.add(partnerFavorite);

      UserTestUtil userTestUtil =
        new ConcreteUserTestUtil()
          .injectLocalUser()
          .injectSpyPartner();

      Property<Object> favoriteLocations = userTestUtil.getPartner().getProperties().property("favoriteLocations");
      favoriteLocations.set(locationList);
      super.beforeActivityLaunched();
    }
  };

  private void givenUserNotOnPartnerFavoriteLocationList() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void whenUserClicksPartnerFavoriteLocationList() {
    onView(withId(R.id.partners_list_button)).perform(click());
  }

  private void thenUserSeesPartnerFavoriteLocations() {
    onView(withId(R.id.partner_locations)).check(matches(isDisplayed()));
    onView(withId(R.id.partners_static_list)).check(matches(hasDescendant(withText("name"))));
  }

  @Test
  public void testPartnerLocationList() {
    givenUserNotOnPartnerFavoriteLocationList();
    whenUserClicksPartnerFavoriteLocationList();
    thenUserSeesPartnerFavoriteLocations();
  }
}

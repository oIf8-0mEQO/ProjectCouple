package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * @author Joseph
 * @since 5/26/16
 */
public class UserViewsPartnerFavoriteLocations {

  @Rule
  public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

  @Before
  public void setup() {
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    FavoriteLocation partnerFavorite = new FavoriteLocation("name", new LatLng(10, 10), 0, VibeTone.getTone());
    List<FavoriteLocation> locationList = new LinkedList<>();
    locationList.add(partnerFavorite);

    UserTestUtil userTestUtil = new ConcreteUserTestUtil()
      .injectLocalUser()
      .mockProperty("name", "Henry")
      .mockProperty("email", "henry@email.com");

    userTestUtil
      .mockPartner()
      .mockFavoriateLocations(() -> locationList);
  }

  private void givenUserNotOnPartnerFavoriteLocationList() {
    onView(withId(R.id.favorite_locations)).perform(click());
  }

  private void whenUserClicksPartnerFavoriteLocationList() {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenUserSeesPartnerFavoriteLocations() {
    onView(withId(R.id.partner_locations)).check(matches(isDisplayed()));
    onView(withId(R.id.partner_locations)).check(matches(hasDescendant(withText("name"))));
  }

  @Test
  public void testPartnerLocationList() {
    givenUserNotOnPartnerFavoriteLocationList();
    whenUserClicksPartnerFavoriteLocationList();
    thenUserSeesPartnerFavoriteLocations();
  }

}

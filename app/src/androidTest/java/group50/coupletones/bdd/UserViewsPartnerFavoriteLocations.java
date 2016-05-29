package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import rx.Observable;

import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Joseph on 5/26/2016.
 */
public class UserViewsPartnerFavoriteLocations {

  @Rule
  public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

  private LocalUser user;
  private Partner partner;
  private CoupleTones app;

  @Before
  public void setup()
  {
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    FavoriteLocation partnerFavorite = new FavoriteLocation("name", new LatLng(10, 10), 0, VibeTone.getTone());
    List<FavoriteLocation> locationList = new LinkedList<>();
    locationList.add(partnerFavorite);
    user = mock(LocalUser.class);
    partner = mock(Partner.class);
    when(user.getPartner()).thenReturn(Observable.just(partner));
    when(partner.getFavoriteLocations()).thenReturn(locationList);

    app = CoupleTones.global().app();
    when(app.isLoggedIn()).thenReturn(true);
    when(app.getLocalUser()).thenReturn(user);
  }

  private void givenUserNotOnPartnerFavoriteLocationList()
  {
    onView(withId(R.id.favorite_locations)).perform(click());
  }

  private void whenUserClicksPartnerFavoriteLocationList()
  {
    onView(withId(R.id.partner_locations)).perform(click());
  }

  private void thenUserSeesPartnerFavoriteLocations()
  {
    onView(withId(R.id.partner_locations)).check(matches(isDisplayed()));
    onView(withId(R.id.partner_locations)).check(matches(hasDescendant(withText("name"))));
  }

  @Test
  public void testPartnerLocationList()
  {
    givenUserNotOnPartnerFavoriteLocationList();
    whenUserClicksPartnerFavoriteLocationList();
    thenUserSeesPartnerFavoriteLocations();
  }

}

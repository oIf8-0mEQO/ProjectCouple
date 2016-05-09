package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/8/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserViewsFavoriteLocationList {
  @Rule
  public ActivityTestRule<MainActivity> notOwnListPage = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private LocalUser mockUser;

  @Before
  public void setup() {
    // Mock DI
    CoupleTones.setGlobal(
        DaggerMockAppComponent
            .builder()
            .mockProximityModule(new MockProximityModule())
            .build()
    );

    // Mock the user
    mockUser = mock(LocalUser.class);
    app = CoupleTones.global().app();
    when(app.isLoggedIn()).thenReturn(true);
    when(app.getLocalUser()).thenReturn(mockUser);
  }
  private void givenUserIsNotViewingOwnList() {
    onView(withId(R.id.partnerLocations)).check(matches(isDisplayed()));
  }

  private void whenUserClicksTheFavesButton() {
    onView(withId(R.id.favoriteLocations)).perform(click());
  }

  @Test
  public void userGoesToFavoriteList() {
    givenUserIsNotViewingOwnList();
    whenUserClicksTheFavesButton();
    onView(withId(R.id.favoriteLocations)).check(matches(isDisplayed()));
  }
}

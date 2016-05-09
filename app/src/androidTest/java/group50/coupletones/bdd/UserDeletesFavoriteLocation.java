package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsListAdapter;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDeletesFavoriteLocation {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
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

  // User has a favorite location
  // Given that I have a favorite location
  // When I click delete on that favorite location
  // Then that location will be removed from the my list of favorite locations (including the name)

  private void givenUserHasFavoriteLocation() {
    onView(withId(R.id.favoriteLocations)).perform(click());
    FavoriteLocationsFragment fragment = (FavoriteLocationsFragment) rule.getActivity().getTabs().get(R.id.favoriteLocations);
    RecyclerView rv = fragment.getFavesList();
    FavoriteLocationsListAdapter adapter = fragment.getAdapter();
    assertThat(adapter.getItemCount()).isEqualTo(0);
  }

  private void whenUserClicksDeleteOnFavoriteLocation() {
    //onView(withId(R.id.delete_location_button)).perform(click());
  }

  private void thenThatLocationWillBeRemovedFromList() {

  }

  @Test
  public void userHasAFavoriteLocation() {
    givenUserHasFavoriteLocation();
    whenUserClicksDeleteOnFavoriteLocation();
  }

  // User does not have a favorite location
  // Given that I do not have a favorite location
  // Then the favorite location list will be empty, and there is no delete button to be pressed

  private void givenUserDoesNotHaveFavoriteLocation() {

  }

  private void thenTheFavoriteLocationListIsEmpty() {

  }

  private void andThereIsNoDeleteButton() {

  }
}

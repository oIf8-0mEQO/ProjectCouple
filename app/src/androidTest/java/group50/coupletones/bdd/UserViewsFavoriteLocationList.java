package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Sharmaine Manalo
 * @since 5/8/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserViewsFavoriteLocationList {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private UserTestUtil testUtil = new ConcreteUserTestUtil();

  @Before
  public void setup() {
    testUtil.injectLocalUser();
  }

  private void givenUserIsNotViewingOwnList() {
    onView(withId(R.id.partner_locations)).check(matches(isDisplayed()));
  }

  private void whenUserClicksTheFavesButton() {
    onView(withId(R.id.favorite_locations)).perform(click());
  }

  private void thenTheListShouldBeShown() {
    onView(withId(R.id.favorite_locations)).check(matches(isDisplayed()));
  }

  @Test
  public void userGoesToFavoriteList() {
    givenUserIsNotViewingOwnList();
    whenUserClicksTheFavesButton();
    thenTheListShouldBeShown();
  }
}

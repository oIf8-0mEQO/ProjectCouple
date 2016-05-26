package group50.coupletones.controller;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Calvin
 * @since 5/8/2016
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PartnerResponseActivityTest {

  @Rule
  public PartnerResponseActivityRule rule = new PartnerResponseActivityRule();
  // Mock user setPartner
  User storedPartner = null;
  private CoupleTones app;
  private LocalUser user;

  @Before
  public void setUp() throws Exception {
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build());

    app = CoupleTones.global().app();
    user = mock(LocalUser.class);

    doAnswer(
      ans -> {
        storedPartner = mock(User.class);
        when(storedPartner.getId()).thenReturn((String) ans.getArguments()[0]);
        return null;
      }
    ).when(user).setPartner(any());

    when(app.getLocalUser()).thenReturn(user);

    // Re-inject the mocked network
    rule.getActivity().app = CoupleTones.global().app();
    rule.getActivity().network = CoupleTones.global().network();
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void sendAcceptResponse() {
    // Click on the button
    onView(withId(R.id.accept_button)).perform(click());
    // Verify response message
    verify(CoupleTones.global().network(), times(1)).send(any());

    // Verify that the user partner is set
    verify(user, times(1)).setPartner("1234");
  }

  public void sendRejectResponse() throws Exception {
    // Click on the button
    onView(withId(R.id.reject_button)).perform(click());
    // Verify response message
    verify(CoupleTones.global().network(), times(1)).send(any());

    // Verify that the user partner not set
    assertThat(user.getPartner()).isNull();
  }

  public static class PartnerResponseActivityRule extends ActivityTestRule<PartnerResponseActivity> {
    public PartnerResponseActivityRule() {
      super(PartnerResponseActivity.class);
    }

    @Override
    protected Intent getActivityIntent() {
      // Generate fake intent to simulate partner request
      Intent activityIntent = super.getActivityIntent();
      activityIntent.putExtra("id", "1234");
      activityIntent.putExtra("name", "partner");
      activityIntent.putExtra("email", "rah005@ucsd.edu");
      return activityIntent;
    }
  }
}


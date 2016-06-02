package group50.coupletones.controller;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.Message;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.subjects.Subject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  private Subject<Message, Message> outgoingStream;

  @Before
  public void setUp() throws Exception {
    // Make sure FCM message is sent
    NetworkManager network = CoupleTones.global().network();
    outgoingStream = spy(Subject.class);
    doReturn(outgoingStream).when(network).getOutgoingStream();

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
    //TODO: Do we want to send a notification?
    //verify(outgoingStream, times(1)).onNext(any());

    // Verify that the user partner is set
    verify(user, times(1)).handlePartnerRequest("1234", true);
  }

  public void sendRejectResponse() throws Exception {
    // Click on the button
    onView(withId(R.id.reject_button)).perform(click());
    // Verify response message
    //TODO: Do we want to send a notification?
    verify(outgoingStream, times(1)).onNext(any());

    // Verify that the user partner not set
    verify(user, times(1)).handlePartnerRequest("1234", false);
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


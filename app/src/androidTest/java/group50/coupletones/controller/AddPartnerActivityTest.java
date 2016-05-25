package group50.coupletones.controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.Button;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.network.message.OutgoingMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Calvin
 * @since 5/7/2016
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddPartnerActivityTest extends ActivityInstrumentationTestCase2<AddPartnerActivity> {

  public NetworkManager network;
  public CoupleTones app;
  private AddPartnerActivity activity;

  public AddPartnerActivityTest() {
    super(AddPartnerActivity.class);
  }

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();

    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build());

    // Stub getLocalUser method
    when(CoupleTones.global().app().getLocalUser()).thenReturn(mock(LocalUser.class));

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
  }

  /**
   * Test the login button click and if it calls sign in for the authenticator
   */
  @Test
  public void testOnClick() {
    activity = getActivity();

    activity.runOnUiThread(() -> {
      OutgoingMessage mockMessage = mock(OutgoingMessage.class);
      when(mockMessage.getString("partner")).thenReturn("rah005@ucsd.edu");
      network = CoupleTones.global().network();

      Button button = (Button) activity.findViewById(R.id.connect_button);
      button.performClick();
      // Verify sign in is called
      verify(network).send(any());
    });
  }
}

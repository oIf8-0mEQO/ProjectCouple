package group50.coupletones.network.gcm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.receiver.PartnerResponseReceiver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Sharmaine Manalo
 * @since 5/7/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PartnerResponseReceiverTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private CoupleTones app;
  private PartnerResponseReceiver partnerResponseReceiver;
  private Message mockMessage;

  public PartnerResponseReceiverTest() {
    super(MainActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    CoupleTones.setGlobal(
        DaggerMockAppComponent
            .builder()
            .build());

    injectInstrumentation(InstrumentationRegistry.getInstrumentation());

    app = CoupleTones.global().app();

    partnerResponseReceiver = new PartnerResponseReceiver(getActivity(), app) {
      @Override
      protected void sendNotification(Context context, String title, String msg) {
        // Do nothing
      }
    };

    mockMessage = mock(Message.class);
    LocalUser mock = mock(LocalUser.class);
    when(app.getLocalUser()).thenReturn(mock);
  }

  @Test
  public void testSetPartner() throws Exception {
    when(mockMessage.getString("requestAccept")).thenReturn("1");
    when(mockMessage.getString("name")).thenReturn("Sharmaine");
    when(mockMessage.getString("email")).thenReturn("hello@sharmaine.me");

    partnerResponseReceiver.onReceive(mockMessage);
    User mock = mock(User.class);
    when(mock.getName()).thenReturn(mockMessage.getString("name"));
    when(mock.getEmail()).thenReturn(mockMessage.getString("email"));
    app.getLocalUser().setPartner(mock);

    verify(app.getLocalUser(), times(2)).setPartner(any());
  }

  @Test
  public void testGetId() throws Exception {
    String id = partnerResponseReceiver.getId();
    assertThat(id).isEqualTo(MessageType.RECEIVE_PARTNER_RESPONSE.value);
  }
}



package group50.coupletones.network.gcm;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.LoginActivity;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageType;
import group50.coupletones.network.receiver.Notification;
import group50.coupletones.network.receiver.PartnerResponseReceiver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Sharmaine Manalo
 * @since 5/7/16
 */

@RunWith(AndroidJUnit4.class)
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
    app.getLocalUser().setPartner(
        new Partner(mockMessage.getString("name"), mockMessage.getString("email")));

    verify(app.getLocalUser(), times(2)).setPartner(any());
    verify(app.getLocalUser(), times(1)).save(any());
  }

  @Test
  public void testGetId() throws Exception {
    String id = partnerResponseReceiver.getId();
    assertThat(id).isEqualTo(MessageType.RECEIVE_PARTNER_RESPONSE.value);
  }
}



package group50.coupletones.network.gcm;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.receiver.PartnerResponseReceiver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sharmaine Manalo
 * @since 5/7/16
 */
public class PartnerResponseReceiverTest extends ApplicationTestCase<CoupleTones> {

  private CoupleTones app;
  private PartnerResponseReceiver partnerResponseReceiver;
  private Message mockMessage;
  private String mockName, mockEmail;

  public PartnerResponseReceiverTest() {
    super(CoupleTones.class);
  }

  @Before
  public void setUp() throws Exception {
    CoupleTones.setComponent(
        DaggerMockAppComponent
            .builder()
            .build());

    when(CoupleTones.component().app().getLocalUser())
        .thenReturn(new MockLocalUser());

    partnerResponseReceiver = new PartnerResponseReceiver(getContext(), app);
    mockMessage = mock(Message.class);
    mockName = mockMessage.getString("name");
    mockEmail = mockMessage.getString("partner");
  }

  @Test
  public void successSetPartner() throws Exception {
    when(mockMessage.getString("requestAccept")).thenReturn("1");
    app.getLocalUser().setPartner(new Partner(mockName, mockEmail));
    assertEquals(app.getLocalUser().getPartner().getName(), mockName);
    assertEquals(app.getLocalUser().getPartner().getEmail(), mockEmail);
  }
}

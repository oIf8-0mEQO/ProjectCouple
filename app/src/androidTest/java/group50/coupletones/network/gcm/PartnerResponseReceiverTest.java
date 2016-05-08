package group50.coupletones.network.gcm;

import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.MockLocalUser;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.network.message.Message;
import group50.coupletones.network.message.MessageType;
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
  }

  @Test
  public void successSetPartner() throws Exception {
    when(mockMessage.getString("requestAccept")).thenReturn("1");
    when(mockMessage.getString("name")).thenReturn("Sharmaine");
    when(mockMessage.getString("partner")).thenReturn("hello@sharmaine.me");

    partnerResponseReceiver.onReceive(mockMessage);
    app.getLocalUser().setPartner(new Partner("Sharmaine", "hello@sharmaine.me"));
    assertEquals(app.getLocalUser().getPartner().getName(), "Sharmaine");
    assertEquals(app.getLocalUser().getPartner().getEmail(), "hello@sharmaine.me");

    verify(app.getLocalUser(), times(1)).save(any());
  }

  @Test
  public void successGetId() {
    String id = partnerResponseReceiver.getId();
    assertThat(id).isEqualTo(MessageType.RECEIVE_PARTNER_RESPONSE.value);
  }

  @Test
  public void failGetId() {
    String id = partnerResponseReceiver.getId();
    assertThat(id).isNotEqualTo(MessageType.RECEIVE_PARTNER_RESPONSE.value);
  }
}

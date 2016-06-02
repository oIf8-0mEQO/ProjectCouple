package group50.coupletones.controller.tab.favoritelocations.map;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.location.VisitedLocationEvent;
import group50.coupletones.di.DaggerMockAppComponent;
import group50.coupletones.di.MockProximityModule;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.OutgoingMessage;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Henry Mao
 * @since 5/29/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProximityNetworkHandlerTest {

  private ProximityNetworkHandler proximityNetworkHandler;
  private UserTestUtil testUtil;

  @Before
  public void setup() {

    // Setup DI
    CoupleTones.setGlobal(
      DaggerMockAppComponent
        .builder()
        .mockProximityModule(new MockProximityModule())
        .build()
    );

    testUtil = new ConcreteUserTestUtil()
      .injectLocalUser();

    testUtil.injectSpyPartner();

    proximityNetworkHandler = new ProximityNetworkHandler(CoupleTones.global().app(), CoupleTones.global().network());
  }

  @Test
  public void testEnterLocation() {
    VisitedLocationEvent location = new VisitedLocationEvent("Home", new LatLng(0, 0), new Date(), new Date(), null);
    proximityNetworkHandler.onEnterLocation(location);

    // Make sure GCM message is sent
    NetworkManager network = CoupleTones.global().network();
    // Verify the GCM message has exactly the correct arguments.
    verify(network, times(1)).send(argThat(new Matcher<OutgoingMessage>() {
      @Override
      public boolean matches(Object item) {
        if (item instanceof OutgoingMessage) {
          OutgoingMessage msg = (OutgoingMessage) item;
          return msg.getString("name").equals("Home") &&
            msg.getDouble("lat") == 0 &&
            msg.getDouble("long") == 0 &&
            msg.getString("time").equals((new SimpleDateFormat("HH:mm", Locale.US)).format(location.getTimeVisited())) &&
            msg.getString("partner").equals("henry@email.com");
        }
        return false;
      }

      @Override
      public void describeMismatch(Object item, Description mismatchDescription) {

      }

      @Override
      public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

      }

      @Override
      public void describeTo(Description description) {

      }
    }));

    // Make sure the visited location is added.
    LocalUser localUser = CoupleTones.global().app().getLocalUser();
    verify(localUser, times(1)).addVisitedLocation(location);
    assertThat(localUser.getVisitedLocations()).hasSize(1);
    assertThat(localUser.getVisitedLocations()).contains(location);
  }
}

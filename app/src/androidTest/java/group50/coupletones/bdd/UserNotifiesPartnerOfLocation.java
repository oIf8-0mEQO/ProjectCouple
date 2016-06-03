package group50.coupletones.bdd;

import android.location.LocationManager;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.LocationNotificationMediator;
import group50.coupletones.controller.tab.favoritelocations.map.MapProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.ProximityManager;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.network.fcm.NetworkManager;
import group50.coupletones.network.fcm.message.Message;
import group50.coupletones.util.sound.VibeTone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.subjects.Subject;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * BDD style test for user notifies partner of location
 *
 * @author Henry Mao
 * @since 5/8/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserNotifiesPartnerOfLocation {

  private UserTestUtil testUtil = new ConcreteUserTestUtil();
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      testUtil.injectLocalUser();
      testUtil.injectSpyPartner();
      super.beforeActivityLaunched();
    }
  };
  private CoupleTones app;
  private ProximityManager proximityManager;
  private LatLng zoneLatLng = new LatLng(32.882, -117.233);
  private FavoriteLocation zone = new FavoriteLocation("Home", zoneLatLng, 0, VibeTone.getDefaultTone().getIndex());
  private Subject<Message, Message> outgoingStream;

  @Before
  public void setup() {
    // Make sure FCM message is sent
    NetworkManager network = CoupleTones.global().network();
    outgoingStream = spy(Subject.class);
    doReturn(outgoingStream).when(network).getOutgoingStream();

    app = CoupleTones.global().app();

    // Create an instance of MapProxManager and register a network handler
    proximityManager = new MapProximityManager(app, CoupleTones.global().timeUtility());
    LocationNotificationMediator locationNotificationMediator = new LocationNotificationMediator();
    proximityManager.getEnterSubject().subscribe(locationNotificationMediator::onEnterLocation);

    //List of the user's favorite locations.
    List<FavoriteLocation> list = new LinkedList<>();
    list.add(zone);

    testUtil.mockFavoriteLocations(() -> list);
  }

  private android.location.Location givenIWasNotPreviousInTheZone() {
    android.location.Location mockLocation = new android.location.Location(LocationManager.GPS_PROVIDER);
    mockLocation.setLatitude(30);
    mockLocation.setLongitude(117);
    mockLocation.setAltitude(0);
    mockLocation.setAccuracy(1);
    mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    mockLocation.setTime(System.currentTimeMillis());
    proximityManager.onLocationChanged(mockLocation);
    return mockLocation;
  }

  private android.location.Location whenIEnterTheZone() {
    android.location.Location mockLocation = new android.location.Location(LocationManager.GPS_PROVIDER);
    mockLocation.setLatitude(zoneLatLng.latitude);
    mockLocation.setLongitude(zoneLatLng.longitude);
    mockLocation.setAltitude(0);
    mockLocation.setAccuracy(1);
    mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    mockLocation.setTime(System.currentTimeMillis());
    proximityManager.onLocationChanged(mockLocation);
    return mockLocation;
  }

  private void thenMyPartnerIsNotified() {
    // Check message sent to server
    verify(outgoingStream, times(1)).onNext(any());
  }

  @Test
  public void userEntersZone() throws Exception {
    givenIWasNotPreviousInTheZone();
    whenIEnterTheZone();
    thenMyPartnerIsNotified();
  }
}

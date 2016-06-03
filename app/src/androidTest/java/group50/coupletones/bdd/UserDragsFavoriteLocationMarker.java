package group50.coupletones.bdd;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.LocationDragMediator;
import group50.coupletones.controller.tab.favoritelocations.map.MapFragment;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.mocker.ConcreteUserTestUtil;
import group50.coupletones.mocker.UserTestUtil;
import group50.coupletones.util.sound.VibeTone;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Joseph on 6/2/2016.
 */
public class UserDragsFavoriteLocationMarker {

  private Marker marker;
  private LocationDragMediator dragHandler;
  private FavoriteLocation location;
  private UserTestUtil userTestUtil;
  private MapFragment map;
  private User user;
  private CoupleTones app;

  @Rule
  public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();
      app = CoupleTones.global().app();

      // Mock the local user
      userTestUtil  = new ConcreteUserTestUtil();
      user = (LocalUser) userTestUtil
                               .injectLocalUser()
                               .get();
    }

    @Override
    protected void afterActivityLaunched() {
      super.afterActivityLaunched();
      map = (MapFragment) rule.getActivity().getTabs().get(R.id.map);
    }
  };

  @Before
  public void setup()
  {
    dragHandler = new LocationDragMediator();
    LinkedList<FavoriteLocation> list = new LinkedList<>();
    user = userTestUtil.mockFavoriteLocations(() -> list).get();
  }

  public void givenUserHasAFavoriteLocation()
  {
    location = new FavoriteLocation("test name", new LatLng(10, 10), 0, VibeTone.getTone(1));
    marker = map.addMarker(new LatLng(10, 10));
    dragHandler.bindPair(marker, location);
  }

  public void whenUserDragsLocationMarker()
  {
    dragHandler.onMarkerDragStart(marker);
    marker = map.addMarker(new LatLng(20, 20));
    dragHandler.onMarkerDragEnd(marker);
  }

  public void thenLocationsPositionWillChange()
  {
    assertThat(user.getFavoriteLocations().get(0).getPosition().equals(new LatLng(20, 20)));
  }

  @Test
  public void userDragsMarker()
  {
    givenUserHasAFavoriteLocation();
    whenUserDragsLocationMarker();
    thenLocationsPositionWillChange();
  }

}

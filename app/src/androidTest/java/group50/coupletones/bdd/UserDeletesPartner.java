package group50.coupletones.bdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import group50.coupletones.CoupleTones;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.sound.VibeTone;

/**
 * @author Sharmaine Manalo
 * @since 5/28/16
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDeletesPartner {

  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
  private CoupleTones app;
  private LocalUser mockUser;

  /**
   * User removes partner
   * Given that the user has a partner
   * When the the user clicks on "Remove Partner" on the Settings Page
   * Then the user's partner is removed
   * And the user is removed as the partner's partner.
   */

  private void givenThatTheUserHasPartner() {

  }

  private void whenUserClicksRemovePartnerOnSettingsPage() {

  }

  private void thenUserPartnerIsRemoved() {

  }

  private void andUserIsRemovedAsPartnersPartner() {

  }

  @Test
  public void userDeletesPartner() {
    
  }

}

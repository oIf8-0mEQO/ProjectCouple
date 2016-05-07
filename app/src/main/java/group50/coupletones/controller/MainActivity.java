package group50.coupletones.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.Authenticator;
import group50.coupletones.auth.GoogleAuthenticator;
import group50.coupletones.auth.user.User;
import group50.coupletones.controller.tab.SettingsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.favoritelocations.map.LocationService;
import group50.coupletones.controller.tab.favoritelocations.map.Map;
import group50.coupletones.controller.tab.partnerslocations.PartnersLocationsFragment;
import group50.coupletones.network.NetworkManager;
import group50.coupletones.util.Taggable;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * The main activity that contains the main tab menu bar. Handles each
 * tab page as a fragment and renders them accordingly.
 */
public class MainActivity extends AppCompatActivity implements
  FavoriteLocationsFragment.Listener,
  PartnersLocationsFragment.Listener, SettingsFragment.Listener,
  OnMenuTabClickListener,
  Taggable {

  @Inject
  public CoupleTones app;

  @Inject
  public NetworkManager network;

  @Inject
  public Authenticator<User, String> auth;


  /**
   * The bottom tab bar handler
   */
  private BottomBar mBottomBar;
  /**
   * A map of IDs to the respective fragments
   */
  private HashMap<Integer, Fragment> tabs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Start Location Services
    Intent i = new Intent(MainActivity.this, LocationService.class);
    startService(i);

    // Dependency Injection
    CoupleTones.component().inject(this);

    setContentView(R.layout.activity_main);

    // Initialize tabs
    tabs = new HashMap<>();
    tabs.put(R.id.partnerLocations, PartnersLocationsFragment.build());
    tabs.put(R.id.favoriteLocations, FavoriteLocationsFragment.build());
    tabs.put(R.id.settings, SettingsFragment.build());
    tabs.put(R.id.map, Map.build());

    mBottomBar = BottomBar.attach(this, savedInstanceState);
    mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, this);

    // Setting custom font for tabs
    mBottomBar.setTypeFace(getString(R.string.pier_sans));

    // Hide bottom bar shadow
    mBottomBar.hideShadow();
  }

  @Override
  protected void onStart() {
    super.onStart();
    ((GoogleAuthenticator) auth).connect();
  }

  @Override
  protected void onStop() {
    super.onStop();
    ((GoogleAuthenticator) auth).disconnect();
  }

  @Override
  public void onMenuTabSelected(
    @IdRes
      int menuItemId) {

    // When a tab is selected, handle switching fragments
    if (tabs.containsKey(menuItemId)) {
      setFragment(tabs.get(menuItemId));
    }

  }

  @Override
  public void onMenuTabReSelected(
    @IdRes
      int menuItemId) {

    // When a tab is selected, handle switching fragments
    if (tabs.containsKey(menuItemId)) {
      setFragment(tabs.get(menuItemId));
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    // Necessary to restore the BottomBar's state, otherwise we would
    // lose the current tab on orientation change.
    mBottomBar.onSaveInstanceState(outState);
  }

  /**
   * Sets the content of the MainActivity with the given fragment
   *
   * @param fragment The fragment to set for the main content
   */
  public void setFragment(Fragment fragment) {
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.fragment_container, fragment) // Replace whatever is in the fragment_container view
      .addToBackStack(null)                       // Add the transaction to the back stack if needed
      .commit();                                  // Commit the transaction
  }

  public HashMap<Integer, Fragment> getTabs() {
    return tabs;
  }

}

package group50.coupletones.controller;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.tab.SettingsFragment;
import group50.coupletones.controller.tab.favoritelocations.FavoriteLocationsFragment;
import group50.coupletones.controller.tab.favoritelocations.map.MapFragment;
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
  PartnersLocationsFragment.Listener,
  OnMenuTabClickListener,
  Taggable {

  @Inject
  public CoupleTones app;

  @Inject
  public NetworkManager network;

  /**
   * The bottom tab bar handler
   */
  private BottomBar mBottomBar;
  /**
   * A map of IDs to the respective fragments
   */
  private HashMap<Integer, Fragment> tabs;

  /**
   * onCreate
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Dependency Injection
    CoupleTones.global().inject(this);

    // Initialize tabs
    tabs = new HashMap<>();
    tabs.put(R.id.partnerLocations, new PartnersLocationsFragment());
    tabs.put(R.id.favoriteLocations, new FavoriteLocationsFragment());
    tabs.put(R.id.settings, new SettingsFragment());
    tabs.put(R.id.map, new MapFragment());

    mBottomBar = BottomBar.attach(this, savedInstanceState);
    mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, this);

    // Setting custom font for tabs
    mBottomBar.setTypeFace(getString(R.string.pier_sans));

    // Hide bottom bar shadow
    mBottomBar.hideShadow();
  }

  /**
   * When Menu Tab is Selected,
   * handles switching fragments
   * @param menuItemId - ID of selected menu item
   */
  @Override
  public void onMenuTabSelected(
    @IdRes
      int menuItemId) {

    // When a tab is selected, handle switching fragments
    if (tabs.containsKey(menuItemId)) {
      setFragment(tabs.get(menuItemId));
    }
  }

  /**
   * When Menu Tab is Re-Selected,
   * handles switching fragments
   * @param menuItemId - ID of selected menu item
   */
  @Override
  public void onMenuTabReSelected(
    @IdRes
      int menuItemId) {

    // When a tab is selected, handle switching fragments
    if (tabs.containsKey(menuItemId)) {
      setFragment(tabs.get(menuItemId));
    }
  }

  /**
   * onSaveInstanceState
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    // Necessary to restore the BottomBar's state, otherwise we would
    // lose the current tab on orientation change.
    mBottomBar.onSaveInstanceState(outState);
  }

  /**
   * Sets the content of the MainActivity with the given fragment
   * @param fragment - The fragment to set for the main content
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

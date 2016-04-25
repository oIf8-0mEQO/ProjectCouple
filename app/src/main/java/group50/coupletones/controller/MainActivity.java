package group50.coupletones.controller;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import group50.coupletones.R;
import group50.coupletones.controller.tab.FavoriteLocationsFragment;

/**
 * The main activity that contains the main tab menu bar. Handles each
 * tab page as a fragment and renders them accordingly.
 */
public class MainActivity extends AppCompatActivity implements
  FavoriteLocationsFragment.Listener, OnMenuTabClickListener {

  private static final String TAG = "MainActivity";

  /**
   * The bottom tab bar handler
   */
  private BottomBar mBottomBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create default fragment
    Fragment defaultFragment = new FavoriteLocationsFragment.Factory().build();
    setFragment(defaultFragment);

    mBottomBar = BottomBar.attach(this, savedInstanceState);
    mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, this);

    // Setting colors for different tabs when there's more than three of them.
    // You can set colors for tabs in three different ways as shown below.
    //TODO: Use constants!
    mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
    mBottomBar.mapColorForTab(1, 0xFF5D4037);
    mBottomBar.mapColorForTab(2, "#7B1FA2");
  }

  @Override
  public void onMenuTabSelected(
    @IdRes
      int menuItemId) {
    Log.d(TAG, "onMenuTabSelected: " + menuItemId);
    switch (menuItemId) {
      case R.id.partnerLocations:
        break;
      case R.id.favoriteLocations:
        break;
      case R.id.settings:
        break;
    }
  }

  @Override
  public void onMenuTabReSelected(
    @IdRes
      int menuItemId) {
    Log.d(TAG, "onMenuTabReSelected: " + menuItemId);
    switch (menuItemId) {
      case R.id.partnerLocations:
        break;
      case R.id.favoriteLocations:
        break;
      case R.id.settings:
        break;
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
   * @param fragment The fragment to set for the main content
   */
  private void setFragment(Fragment fragment) {
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.fragment_container, fragment) // Replace whatever is in the fragment_container view
      .addToBackStack(null) // Add the transaction to the back stack if needed
      .commit();  // Commit the transaction
  }
}

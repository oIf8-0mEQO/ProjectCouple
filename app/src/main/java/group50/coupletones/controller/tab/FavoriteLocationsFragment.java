package group50.coupletones.controller.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import group50.coupletones.R;

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 * Activities that contain this fragment must implement the {@link Listener} interface to handle interaction events.
 * Use the {@link FavoriteLocationsFragment#build} factory class to create an instance of this fragment.
 */
public class FavoriteLocationsFragment extends TabFragment<FavoriteLocationsFragment.Listener> {

  public FavoriteLocationsFragment() {
    super(Listener.class);
  }

  @Override
  protected int getResourceId() {
    return R.layout.fragment_favorite_locations;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      //TODO: Read arguments
    }
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface Listener {
    // TODO: Fill with interface methods
  }

  /**
   * Use this factory method to create a new instance of FavoriteLocationsFragment.
   */

  public static FavoriteLocationsFragment build() {
    FavoriteLocationsFragment fragment = new FavoriteLocationsFragment();
    Bundle args = new Bundle();
    // TODO: Set arguments
    fragment.setArguments(args);
    return fragment;
  }
}

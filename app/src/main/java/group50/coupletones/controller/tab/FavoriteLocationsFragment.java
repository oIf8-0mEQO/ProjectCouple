package group50.coupletones.controller.tab;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_settings, container, false);
    Typeface pierSans = Typeface.createFromAsset(getActivity().getAssets(),
      getString(R.string.pier_sans));
    return v;
  }

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
      case R.id.btn_search:

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
}

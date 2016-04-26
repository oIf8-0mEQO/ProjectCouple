package group50.coupletones.controller.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import group50.coupletones.R;

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 * Activities that contain this fragment must implement the {@link Listener} interface to handle interaction events.
 * Use the {@link SettingsFragment#build} factory class to create an instance of this fragment.
 */
public class SettingsFragment extends TabFragment<SettingsFragment.Listener> {

    public SettingsFragment() {
        super(Listener.class);
    }

    /**
     * Use this factory method to create a new instance of SettingsFragment.
     */
    public static SettingsFragment build() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        // TODO: Set arguments
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_settings;
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
}

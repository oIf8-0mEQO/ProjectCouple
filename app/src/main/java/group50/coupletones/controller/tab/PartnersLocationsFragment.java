package group50.coupletones.controller.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group50.coupletones.R;

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 * Activities that contain this fragment must implement the {@link Listener} interface to handle interaction events.
 * Use the {@link PartnersLocationsFragment#build} factory class to create an instance of this fragment.
 */
public class PartnersLocationsFragment extends TabFragment<PartnersLocationsFragment.Listener> {

    private RecyclerView favesList;

    public PartnersLocationsFragment() {
        super(Listener.class);
    }

    /**
     * Use this factory method to create a new instance of FavoriteLocationsFragment.
     */
    public static PartnersLocationsFragment build() {
        PartnersLocationsFragment fragment = new PartnersLocationsFragment();
        Bundle args = new Bundle();
        // TODO: Set arguments
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_partners_locations;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //TODO: Read arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_locations, container, false);
        favesList = (RecyclerView) v.findViewById(R.id.favorite_locations_list);
        return v;
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

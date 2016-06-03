package group50.coupletones.controller.tab.partnerslocations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.TabFragment;
import rx.Observable;

/**
 * @author Joanne Cho
 */

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 */
public class PartnersLocationsFragment extends TabFragment<Object> {
  private RecyclerView partnersList;
  private ListAdapterPartnerVisited adapter;

  public PartnersLocationsFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   *
   * @return - Partner's locations fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_partners_locations;
  }

  /**
   * onCreateView
   *
   * @return - View of app
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    CoupleTones app = CoupleTones.global().app();

    View v = inflater.inflate(R.layout.fragment_partners_locations, container, false);
    partnersList = (RecyclerView) v.findViewById(R.id.vibetone_recycler_view);
    partnersList.setLayoutManager(new LinearLayoutManager(getActivity()));

    adapter = new ListAdapterPartnerVisited(
      app.getLocalUser() != null ?
        app.getLocalUser().getPartner() : Observable.empty(),
      getActivity()
    );

    partnersList.setAdapter(adapter);

    // Clicking the List Button in the Upper Right Corner takes user to Partner's Favorites List
    ImageButton partnerFaves;
    partnerFaves = (ImageButton) v.findViewById(R.id.partners_list_button);
    partnerFaves.setOnClickListener(view -> ((MainActivity) getActivity()).setFragment(new PartnersFavoritesFragment()));

    return v;
  }
}

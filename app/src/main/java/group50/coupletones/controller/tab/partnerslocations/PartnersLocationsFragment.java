package group50.coupletones.controller.tab.partnerslocations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import group50.coupletones.R;
import group50.coupletones.controller.tab.TabFragment;

/**
 * @author Joanne Cho
 */

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 */
public class PartnersLocationsFragment extends TabFragment<Object> {
  private RecyclerView partnersList;
  private ListAdapterPartner adapter;

  public PartnersLocationsFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   * @return - Partner's locations fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_partners_locations;
  }

  /**
   * onCreateView
   * @return - View of app
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_partners_locations, container, false);
    partnersList = (RecyclerView) v.findViewById(R.id.partners_location_list);
    partnersList.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new ListAdapterPartner(PartnerLocationsData.getPartnerLocations(), getActivity());
    partnersList.setAdapter(adapter);
    return v;
  }
}

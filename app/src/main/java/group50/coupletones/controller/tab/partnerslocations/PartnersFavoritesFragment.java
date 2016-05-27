package group50.coupletones.controller.tab.partnerslocations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.tab.TabFragment;

/**
 * @author Joanne Cho
 */

/**
 * A simple {@link Fragment} subclass for the Partner's Favorite Locations tab.
 */
public class PartnersFavoritesFragment extends TabFragment<Object> {
  private RecyclerView partnersList;
  private ListAdapterPartnerFavorites adapter;

  public PartnersFavoritesFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   *
   * @return - Partner's favorite locations fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_partners_list;
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

    View v = inflater.inflate(R.layout.fragment_partners_list, container, false);
    partnersList = (RecyclerView) v.findViewById(R.id.partners_static_list);
    partnersList.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new ListAdapterPartnerFavorites(app.getLocalUser().getPartner(), getActivity());
    partnersList.setAdapter(adapter);
    return v;
  }
}

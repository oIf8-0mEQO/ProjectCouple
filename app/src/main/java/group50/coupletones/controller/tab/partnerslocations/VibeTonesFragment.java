package group50.coupletones.controller.tab.partnerslocations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import group50.coupletones.R;
import group50.coupletones.controller.tab.TabFragment;

/**
 * @author Joanne Cho
 */

/**
 * A simple {@link Fragment} subclass for the Favorite Locations tab.
 */
public class VibeTonesFragment extends TabFragment<Object> {
  private RecyclerView vibeTonesList;
  private ListAdapterVibeTones adapter;

  public VibeTonesFragment() {
    super(Object.class);
  }

  /**
   * getResourceID
   * @return - VibeTones fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_edit_vibetone;
  }

  /**
   * onCreateView
   * @return - View of app
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_edit_vibetone, container, false);
    vibeTonesList = (RecyclerView) v.findViewById(R.id.partners_location_list);
    vibeTonesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new ListAdapterVibeTones(VibeTonesData.getVibeTones(), getActivity());
    vibeTonesList.setAdapter(adapter);
    return v;
  }
}

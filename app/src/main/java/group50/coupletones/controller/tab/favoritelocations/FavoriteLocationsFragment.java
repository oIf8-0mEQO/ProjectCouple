package group50.coupletones.controller.tab.favoritelocations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import group50.coupletones.controller.tab.favoritelocations.map.MapFragment;
import group50.coupletones.controller.tab.partnerslocations.PartnersFavoritesFragment;

import javax.inject.Inject;

/**
 * A {@link Fragment} subclass for the Favorite Locations tab.
 */
public class FavoriteLocationsFragment extends TabFragment<Object> {

  @Inject
  public CoupleTones app;
  public FavoriteLocationsListAdapter adapter;
  public RecyclerView favesList;

  public FavoriteLocationsFragment() {
    super(Object.class);
  }

  public RecyclerView getFavesList() {
    return favesList;
  }

  public FavoriteLocationsListAdapter getAdapter() {
    return adapter;
  }

  /**
   * Gets Resrouce ID
   *
   * @return - favorite location fragment
   */
  @Override
  protected int getResourceId() {
    return R.layout.fragment_favorite_locations;
  }

  /**
   * onCreate
   *
   * @param savedInstanceState - Bundle
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.global().inject(this);
  }

  /**
   * onCreateView
   *
   * @param inflater           - LayoutInflater
   * @param container          - ViewGroup
   * @param savedInstanceState - Bundle
   * @return - View v
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_favorite_locations, container, false);
    favesList = (RecyclerView) v.findViewById(R.id.favorite_locations_list);
    favesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new FavoriteLocationsListAdapter(this, getActivity());
    favesList.setAdapter(adapter);


    // Clicking on the add button will open the map fragment
    v.findViewById(R.id.add_favorite_location).setOnClickListener(evt -> {
      MainActivity act = (MainActivity) getActivity();
      ((MapFragment) act.getTabs().get(R.id.map)).setIsUser(true);
      act.setFragment(act.getTabs().get(R.id.map));
    });


    return v;
  }
}

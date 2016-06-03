package group50.coupletones.controller.tab.partnerslocations;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.util.Taggable;
import group50.coupletones.util.sound.VibeTone;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Joanne Cho
 * @since 5/25/16
 */

/**
 * Partner Locations List Adapter Class
 */
public class ListAdapterVibeTones extends RecyclerView.Adapter<ListAdapterVibeTones.ListViewHolder>
  implements Taggable {

  @Inject
  public CoupleTones app;

  private List<VibeTone> tones;
  private LayoutInflater inflater;
  private int locationIndex;
  private VibeTonesFragment fragment;

  /**
   * Partner list adapter
   *
   * @param tones - VibeTones location tones
   */
  public ListAdapterVibeTones(int locationIndex, List<VibeTone> tones, VibeTonesFragment fragment) {
    this.inflater = LayoutInflater.from(fragment.getActivity());
    this.tones = tones;
    this.locationIndex = locationIndex;
    this.fragment = fragment;
    CoupleTones.global().inject(this);
  }

  /**
   * List view holder for VibeTones
   *
   * @param parent - ViewGroup
   * @return - ListViewholder
   */
  @Override
  public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.vibetone_list_item, parent, false);
    return new ListViewHolder(v);
  }

  /**
   * View holder for partner locations
   *
   * @param holder   - ListViewHolder
   * @param position - Partner location's position
   */
  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    // position is the ID of the vibetone
    VibeTone tone = tones.get(position);
    holder.name.setText(tone.getName());

    // Set the VibeTone for this location.
    View icon = holder.container.findViewById(R.id.list_vibetone_icon);
    icon.setOnClickListener(view -> {
      Log.v(getTag(), "Clicked on VibeTone item");
      // Set the vibe tone for this location
      app.getLocalUser()
        .getPartner()
        .filter(partner -> partner != null)
        .subscribe(partner -> {
          Log.v(getTag(), "Setting VibeTone: " + position);
          partner.setVibeTone(locationIndex, position);
        });

      // Go back to Partner's favorite location fragment
      ((MainActivity) fragment.getActivity()).setFragment(new PartnersFavoritesFragment());
    });
  }

  /**
   * Gets Item Count
   *
   * @return - number of items
   */
  @Override
  public int getItemCount() {
    return tones.size();
  }

  /**
   * Adds Recycler View to List View Holder
   */
  class ListViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private ImageView icon;
    private View container;
    private CardView cv;

    /**
     * ListViewHolder Constructor
     *
     * @param itemView - View
     */
    public ListViewHolder(View itemView) {
      super(itemView);
      cv = (CardView) itemView.findViewById(R.id.cv);
      name = (TextView) itemView.findViewById(R.id.list_vibetone_name);
      container = itemView.findViewById(R.id.list_item);
    }
  }
}
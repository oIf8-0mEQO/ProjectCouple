package group50.coupletones.controller.tab.partnerslocations;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;

import java.util.List;

/**
 * @author Joanne Cho
 * @since 5/25/16
 */

/**
 * Partner Locations List Adapter Class
 */
public class ListAdapterVibeTones extends RecyclerView.Adapter<ListAdapterVibeTones.ListViewHolder> {

  private Activity activity;
  private List<PartnerLocation> data;
  private LayoutInflater inflater;

  /**
   * Partner list adapter
   * @param data - VibeTones location data
   */
  public ListAdapterVibeTones(List<PartnerLocation> data, Activity context) {
    this.inflater = LayoutInflater.from(context);
    this.data = data;
    this.activity = context;
  }

  /**
   * List view holder for VibeTones
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
   * @param holder - ListViewHolder
   * @param position - Partner location's position
   */
  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    PartnerLocation tone = data.get(position);
    holder.name.setText(tone.getName());
  }

  /**
   * Gets Item Count
   * @return - number of items
   */
  @Override
  public int getItemCount() {
    return data.size();
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
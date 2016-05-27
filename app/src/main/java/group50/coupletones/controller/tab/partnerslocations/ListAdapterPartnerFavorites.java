package group50.coupletones.controller.tab.partnerslocations;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.R;

import java.util.List;

/**
 * @author joannecho
 * @since 5/25/16
 */

/**
 * Partner's favorite locations List Adapter Class
 */
public class ListAdapterPartnerFavorites extends RecyclerView.Adapter<ListAdapterPartnerFavorites.ListViewHolder> {

  private List<PartnerLocation> data;
  private LayoutInflater inflater;

  /**
   * Partner's Favorites list adapter
   * @param data - Partner location data
   */
  public ListAdapterPartnerFavorites(List<PartnerLocation> data, Context context) {
    this.inflater = LayoutInflater.from(context);
    this.data = data;
  }

  /**
   * List view holder for partner's favorite locations
   * @param parent - ViewGroup
   * @return - ListViewholder
   */
  @Override
  public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.partner_fave_item, parent, false);
    return new ListViewHolder(v);
  }

  /**
   * View holder for partner's favorite locations
   * @param holder - ListViewHolder
   * @param position - Partner location's position
   */
  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    PartnerLocation location = data.get(position);
    holder.name.setText(location.getName());
    holder.address.setText(location.getAddress());
    holder.icon.setImageResource(location.getIconId());
    holder.vibeTone.setText(location.getVibeTone());

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

    private TextView name, address, vibeTone;
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
      name = (TextView) itemView.findViewById(R.id.list_item_name);
      address = (TextView) itemView.findViewById(R.id.list_item_address);
      icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
      vibeTone = (TextView) itemView.findViewById(R.id.list_item_vibetone);
      container = itemView.findViewById(R.id.list_item);
    }
  }
}
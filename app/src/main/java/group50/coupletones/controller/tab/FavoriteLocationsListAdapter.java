package group50.coupletones.controller.tab;

import android.content.Context;
import android.location.Address;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.R;
import group50.coupletones.map.FavoriteLocation;

import java.util.List;

/**
 * Created by sharmaine on 4/29/16.
 */
public class FavoriteLocationsListAdapter extends RecyclerView.Adapter<FavoriteLocationsListAdapter.ListViewHolder> {

  private List<FavoriteLocation> data;
  private LayoutInflater inflater;

  public FavoriteLocationsListAdapter(List<FavoriteLocation> data, Context context) {
    this.inflater = LayoutInflater.from(context);
    this.data = data;
  }

  @Override
  public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.list_item, parent, false);
    return new ListViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    FavoriteLocation location = data.get(position);
    holder.name.setText(location.getName());
    holder.address.setText(location.getName());
    Address address = location.getAddress();
    holder.address.setText(address != null ? address.getFeatureName() : "");
    holder.icon.setImageResource(R.drawable.target_icon);
    //TODO: Implement custom icons?
    //holder.icon.setImageResource(location.getIconId());
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListViewHolder extends RecyclerView.ViewHolder {

    private TextView name, address;
    private ImageView icon;
    private View container;
    private CardView cv;

    public ListViewHolder(View itemView) {
      super(itemView);
      cv = (CardView) itemView.findViewById(R.id.cv);
      name = (TextView) itemView.findViewById(R.id.list_item_name);
      address = (TextView) itemView.findViewById(R.id.list_item_address);
      icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
      container = itemView.findViewById(R.id.list_item);
    }
  }
}


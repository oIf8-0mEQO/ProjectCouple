package group50.coupletones.controller.tab.favoritelocations;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.controller.tab.partnerslocations.VibeTonesFragment;

import javax.inject.Inject;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * @author Sharmaine Manalo
 * @since 4/29/16
 */

/**
 * Favorite locations list adapter class
 */
public class FavoriteLocationsListAdapter extends RecyclerView.Adapter<FavoriteLocationsListAdapter.ListViewHolder> {

  @Inject
  public CoupleTones app;

  private LayoutInflater inflater;
  private FavoriteLocationsFragment fragment;

  private Context context;


  /**
   * Favorite Locations List Adapter
   *
   * @param - context
   */
  public FavoriteLocationsListAdapter(FavoriteLocationsFragment fragment, Context context) {
    this.inflater = LayoutInflater.from(context);
    this.fragment = fragment;
    this.context = context;

    CoupleTones.global().inject(this);
  }

  /**
   * List view holder
   *
   * @param parent - ViewGroup
   * @return - ListViewHolder
   */
  @Override
  public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.list_item, parent, false);
    return new ListViewHolder(v);
  }

  /**
   * View holder for fragment
   *
   * @param holder   - ListViewHolder
   * @param position - Favorite location's position
   */
  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    FavoriteLocation location = app.getLocalUser().getFavoriteLocations().get(position);
    holder.name.setText(location.getName());
    holder.address.setText(location.getName());
    Address address = location.getAddress();
    if (address != null) {
      if (address.getLocality() != null) {
        holder.address.setText(address.getLocality() + ", " + address.getAdminArea());
      } else {
        holder.address.setText(address.getAdminArea());
      }
    } else {
      holder.address.setText("");
    }
    holder.icon.setImageResource(R.drawable.myfave_icon);
    //TODO: Implement custom icons?
    //holder.icon.setImageResource(location.getIconId());

    // Clicking on the edit location icon takes you to the Edit Location activity
    holder.itemView.findViewById(R.id.edit_location_icon)
      .setOnClickListener((view) -> {
        Intent intent = new Intent(context, EditLocationActivity.class);
        context.startActivity(intent);
      });

    // Clicking on the delete icon deletes the location from the list
    holder.itemView.findViewById(R.id.delete_location_icon)
      .setOnClickListener(evt -> {
            app.getLocalUser().removeFavoriteLocation(location);
            fragment.adapter.notifyDataSetChanged();
          }
      );
  }

  /**
   * getItemCount
   *
   * @return - number of items
   */
  @Override
  public int getItemCount() {
    return app.getLocalUser().getFavoriteLocations().size();
  }

  /**
   * Adds Recycler View to List View Holder
   */
  class ListViewHolder extends RecyclerView.ViewHolder {

    private TextView name, address;
    private ImageView icon;
    private View container;
    private CardView cv;

    /**
     * ListViewHolder
     *
     * @param itemView - View
     */
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


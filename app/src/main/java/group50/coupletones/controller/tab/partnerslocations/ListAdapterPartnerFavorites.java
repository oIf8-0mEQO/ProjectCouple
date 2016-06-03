package group50.coupletones.controller.tab.partnerslocations;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import group50.coupletones.R;
import group50.coupletones.auth.user.Partner;
import group50.coupletones.controller.MainActivity;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author joannecho
 * @since 5/25/16
 */

/**
 * Partner's favorite locations List Adapter Class
 */
public class ListAdapterPartnerFavorites extends RecyclerView.Adapter<ListAdapterPartnerFavorites.ListViewHolder> {

  /**
   * The user's partner. Null if none exists.
   */
  private List<FavoriteLocation> locations = new LinkedList<>();

  private LayoutInflater inflater;

  private Activity activity;

  private CompositeSubscription subs = new CompositeSubscription();

  /**
   * Partner's Favorites list adapter
   *
   * @param partnerObservable - The partnerObservable object
   */
  public ListAdapterPartnerFavorites(Observable<Partner> partnerObservable, Activity context) {
    this.inflater = LayoutInflater.from(context);
    this.activity = context;

    this.subs.add(
      partnerObservable
        .filter(partner -> partner != null)
        .subscribe(partner -> {
          // Initial list
          setLocations(partner.getFavoriteLocations());

          partner
            .observable("favoriteLocations", List.class)
            .subscribe(this::setLocations);
        })
    );
  }

  private void setLocations(List<FavoriteLocation> locations) {
    this.locations = locations != null ? locations : Collections.emptyList();
    notifyDataSetChanged();
  }

  /**
   * List view holder for partner's favorite locations
   *
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
   *
   * @param holder   - ListViewHolder
   * @param position - Partner location's position
   */
  @Override
  public void onBindViewHolder(ListViewHolder holder, int position) {
    // React to partner location edits
    FavoriteLocation location = locations.get(position);
    holder.name.setText(location.getName());
    holder.address.setText(location.getAddress() != null ? location.getAddress().getLocality() : "");
    holder.icon.setImageResource(R.drawable.partnerfave_icon);
    holder.vibeTone.setText(location.getVibetone().getName());

    // Clicking the Edit Button takes user to the List of VibeTones to choose from
    ImageButton editButton;
    editButton = (ImageButton) holder.itemView.findViewById(R.id.edit_location_icon);
    editButton.setOnClickListener(view -> ((MainActivity) activity).setFragment(new VibeTonesFragment(position)));

  }

  /**
   * Gets Item Count
   *
   * @return - number of items
   */
  @Override
  public int getItemCount() {
    return locations.size();
  }

  @Override
  public void onViewRecycled(ListViewHolder holder) {
    subs.unsubscribe();
    subs.clear();
    super.onViewRecycled(holder);
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
     *
     * @param itemView - View
     */
    public ListViewHolder(View itemView) {
      super(itemView);
      cv = (CardView) itemView.findViewById(R.id.cv);
      name = (TextView) itemView.findViewById(R.id.list_item_name);
      address = (TextView) itemView.findViewById(R.id.list_item_address);
      icon = (ImageView) itemView.findViewById(R.id.list_vibetone_icon);
      vibeTone = (TextView) itemView.findViewById(R.id.list_item_vibetone);
      container = itemView.findViewById(R.id.list_item);
    }
  }
}
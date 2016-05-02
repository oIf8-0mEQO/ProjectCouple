package group50.coupletones;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sharmaine on 4/29/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<FaveLocation> data;
    private LayoutInflater inflater;

    public ListAdapter (List<FaveLocation> data, Context context) {
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
        FaveLocation location = data.get(position);
        holder.name.setText(location.getName());
        holder.address.setText(location.getAddress());
        holder.icon.setImageResource(location.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView name, address;
        private ImageView icon;
        private View container;

        public ListViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.list_item_name);
            address = (TextView)itemView.findViewById(R.id.list_item_address);
            icon = (ImageView)itemView.findViewById(R.id.list_item_icon);
            container = itemView.findViewById(R.id.list_item);
        }
    }
}


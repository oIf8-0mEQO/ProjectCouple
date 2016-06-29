package group50.coupletones.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joseph on 6/25/2016.
 */
public class FavoriteLocation extends  Location implements Storable {

    public FavoriteLocation(String name, LatLng position)
    {
        super(name, position);
    }

    @Override
    public void setName(String name)
    {
        super.setName(name);
    }

    @Override
    public void setPosition(LatLng position)
    {
        super.setPosition(position);
    }

}

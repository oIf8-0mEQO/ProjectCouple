package group50.coupletones.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joseph on 6/25/2016.
 */
public class FavoriteLocation implements Location {

    private String name;
    private LatLng position;

    public FavoriteLocation()
    {
        setName("");
        setPosition(new LatLng(0, 0));
    }
    public FavoriteLocation(String name, LatLng position)
    {
        setName(name);
        setPosition(position);
    }

    public void setPosition(LatLng position)
    {
        this.position = position;
    }
    public LatLng getPosition()
    {
        return position;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

}

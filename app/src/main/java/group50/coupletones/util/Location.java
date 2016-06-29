package group50.coupletones.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joseph on 6/25/2016.
 */
public abstract class Location implements Storable {

    private String name;
    private LatLng position;

    public Location(String name, LatLng position)
    {
        setName(name);
        setPosition(position);
    }

    protected void setPosition(LatLng position)
    {
        this.position = position;
    }
    public LatLng getPosition()
    {
        return position;
    }

    protected void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

}
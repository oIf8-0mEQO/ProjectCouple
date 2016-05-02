package group50.coupletones.map;

import com.google.android.gms.maps.model.LatLng;

import group50.coupletones.map.Location;

/**
 * Created by Joseph on 6/25/2016.
 */
public class FavoriteLocation implements Location {

    private String name;
    private LatLng position;
    private long time;

    public FavoriteLocation()
    {
        setName("");
        setPosition(new LatLng(0, 0));
        time = 0;
    }
    public FavoriteLocation(String name, LatLng position)
    {
        setName(name);
        setPosition(position);
        time = 0;
    }
    public FavoriteLocation(String name, LatLng position, long time)
    {
        setName(name);
        setPosition(position);
        this.time = time;
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

    public void setCooldown()
    {
        time = System.currentTimeMillis();
    }
    /**@Return true if the location is on cooldown, otherwise false.*/
    public boolean isOnCooldown()
    {
        return (System.currentTimeMillis() - time > 600000);
    }

}

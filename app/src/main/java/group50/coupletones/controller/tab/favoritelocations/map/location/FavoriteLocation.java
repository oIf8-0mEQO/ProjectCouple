package group50.coupletones.controller.tab.favoritelocations.map.location;

import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.util.storage.Storable;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

/**
 * @author Joseph Cox
 * @since 6/25/2016
 */

/**
 * Represents a Favorite Location
 */
public class FavoriteLocation implements Location, Storable {

  @Inject
  public AddressProvider addressProvider;
  private String name;
  private LatLng position;
  private long time;
  private static int COOL_DOWN_TIME = 600000;

  /**
   * Default constructor with meaningless initial values.
   */
  public FavoriteLocation() {
    this("", new LatLng(0, 0), 0);
  }

  /**
   * Creates a favorite location that is off cooldown
   *
   * @param name     user given name of the location
   * @param position gps coordinates of the location
   */
  public FavoriteLocation(String name, LatLng position) {
    this(name, position, 0);
  }

  /**
   * @param name     user given name of the location
   * @param position gps coordinates of the location
   * @param time     sets the cooldown as if the location was last triggered at the given time
   */
  public FavoriteLocation(String name, LatLng position, long time) {
    //DI
    CoupleTones.global().inject(this);

    setName(name);
    setPosition(position);
    this.time = time;
  }

  /**
   *
   * @return Latitude-Longitude of the position
   */
  @Override
  public LatLng getPosition() {
    return position;
  }

  /**
   *
   * @param position Latitude-Longitude of the position
   */
  public void setPosition(LatLng position) {
    this.position = position;
  }

  /**
   *
   * @return Name of location
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   *
   * @param name Name of location
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return Address of position
   */
  @Override
  public Address getAddress() {
    return addressProvider.getAddressFromPosition(position);
  }

  /**
   * Sets cool down time for location
   */
  public void setCooldown() {
    time = System.currentTimeMillis();
  }

  /**
   * @return true if the location is on cooldown, otherwise false.
   */
  public boolean isOnCooldown() {
    return (System.currentTimeMillis() - time < COOL_DOWN_TIME);
  }

  /**
   * @return the most recent time this location was visited as a long.
   */
  protected long getTime() {
    return time;
  }

  /**
   *
   * @param storage - storage to save to
   */
  @Override
  public void save(Storage storage) {
    storage.setString("name", name);
    storage.setFloat("lat", (float) position.latitude);
    storage.setFloat("long", (float) position.longitude);
  }

  /**
   *
   * @param storage - storage to save to
   */
  @Override
  public void load(Storage storage) {
    name = storage.getString("name");
    position = new LatLng(storage.getFloat("lat"), storage.getFloat("long"));
  }
}

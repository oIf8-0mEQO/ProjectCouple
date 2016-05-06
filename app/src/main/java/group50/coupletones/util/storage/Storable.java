package group50.coupletones.util.storage;

/**
 * Created by Calvin on 4/25/2016.
 */
public interface Storable {


  /**
   * Save User data onto phone
   *
   * @param storage
   */
  public void save(Storage storage);

  /**
   * Load User data from phone
   *
   * @param storage
   */
  public void load(Storage storage);
}
package group50.coupletones.auth.user;

import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.storage.Storage;

import java.util.Collections;
import java.util.List;

/**
 * Local user mock
 *
 * @author Henry Mao
 * @since 5/6/16
 */
public class MockLocalUser implements LocalUser {

  private User partner;

  /**
   * Functions that gets and sets Mock Information
   * Data of a Mock Local user
   */
  @Override
  public String getId() {
    return "mockuser";
  }

  @Override
  public String getName() {
    return "Mock User";
  }

  @Override
  public String getEmail() {
    return "mock@mock.com";
  }

  @Override
  public List<FavoriteLocation> getFavoriteLocations() {
    return Collections.emptyList();
  }

  @Override
  public User getPartner() {
    return partner;
  }

  @Override
  public void setPartner(User partner) {
    this.partner = partner;
  }

  @Override
  public void save(Storage s) {

  }

  @Override
  public void load(Storage s) {

  }
}

package group50.coupletones.controller.tab.favoritelocations.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.controller.tab.favoritelocations.map.location.UserFavoriteLocation;
import group50.coupletones.util.storage.Storage;

import javax.inject.Inject;

/**
 * @author Joseph Cox
 * @since 5/7/2016
 */

/**
 * Location click handler for Google Map
 */
public class LocationClickHandler implements GoogleMap.OnMapClickListener {

  @Inject
  public CoupleTones app;

  private MapFragment map;

  /**
   * Location click handler
   *
   * @param map - map fragment
   */
  public LocationClickHandler(MapFragment map) {
    this.map = map;
    CoupleTones.global().inject(this);
  }

  /**
   * onMapClick
   *
   * @param latLng - latitude-longitude of position
   */
  @Override
  public void onMapClick(LatLng latLng) {
    AlertDialog.Builder builder = new AlertDialog.Builder(map.getContext());
    builder.setTitle(R.string.location_name_box);
    EditText input = new EditText(map.getContext());
    input.setId(R.id.edit_favorite_location_name_dialog);
    input.setInputType(InputType.TYPE_CLASS_TEXT);
    builder.setView(input);
    builder.setPositiveButton(R.string.location_name_accept, new EventOnAccept(app, map, input, latLng));
    builder.show();
  }

  /**
   * EventOnAccept class
   */
  public static class EventOnAccept implements DialogInterface.OnClickListener {
    @Inject
    public CoupleTones app;
    private EditText input;
    private LatLng position;
    private MapFragment map;

    /**
     * EventOnAccept
     *
     * @param text     - input text
     * @param position - Latitude-Longitude position
     * @param text     Input text
     * @param position Latitude-Longitude position
     */
    public EventOnAccept(CoupleTones app, MapFragment map, EditText text, LatLng position) {
      this.app = app;
      this.map = map;
      this.input = text;
      this.position = position;
    }

    /**
     * Handles the dialog click event
     *
     * @param dialog The dialog
     * @param which  Which button was clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
      // Save the favorite location
      String name = input.getText().toString();
      UserFavoriteLocation clickedLocation = new UserFavoriteLocation(name, position, 0);
      app.getLocalUser().getFavoriteLocations().add(clickedLocation);
      app.getLocalUser().save(new Storage(map.getActivity().getSharedPreferences(Storage.PREF_FILE_USER, Context.MODE_PRIVATE)));

      moveMap(clickedLocation);
    }

    protected void moveMap(UserFavoriteLocation clickedLocation) {
      // Move the camera
      map.moveMap(clickedLocation.getPosition());
      map.populateMap();
    }
  }

}
package group50.coupletones.controller.tab.favoritelocations.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.util.storage.Storage;

/**
 * Created by Joseph on 5/7/2016.
 */
public class AddLocationClicker implements GoogleMap.OnMapClickListener {

  @Inject
  public CoupleTones app;

  private Map map;

  public AddLocationClicker(Map map) {
    this.map = map;
  }

  @Override
  public void onMapClick(LatLng latLng) {
    AlertDialog.Builder builder = new AlertDialog.Builder(map.getContext());
    builder.setTitle(R.string.location_name_box);
    final EditText input = new EditText(map.getContext());
    input.setInputType(InputType.TYPE_CLASS_TEXT);
    builder.setView(input);
    builder.setPositiveButton(R.string.location_name_accept, new EventOnAccept(input, latLng));
    builder.show();
  }

  private class EventOnAccept implements DialogInterface.OnClickListener
  {
    private EditText input;
    private LatLng position;

    public EventOnAccept(EditText text, LatLng position)
    {
      this.input = text;
      this.position = position;
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
      // Save the favorite location
      String name = input.getText().toString();
      FavoriteLocation clickedLocation = new FavoriteLocation(name, position);
      app.getLocalUser().getFavoriteLocations().add(clickedLocation);
      app.getLocalUser().save(new Storage(map.getActivity().getSharedPreferences(Storage.PREF_FILE_USER, Context.MODE_PRIVATE)));

      // Move the camera
      map.moveMap(clickedLocation.getPosition());
      map.populateMap();
    }
  }

}
package group50.coupletones.controller.tab.favoritelocations;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import group50.coupletones.CoupleTones;
import group50.coupletones.R;
import group50.coupletones.auth.user.LocalUser;
import group50.coupletones.controller.tab.favoritelocations.map.location.FavoriteLocation;
import group50.coupletones.util.FormatUtility;

import javax.inject.Inject;
import java.util.List;

/**
 * Activity for Editing Location
 */
public class EditLocationActivity extends AppCompatActivity {

  @Inject
  public CoupleTones app;
  @Inject
  public FormatUtility formatUtility;

  private TextView textLocationName;
  private TextView textSaveChange;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CoupleTones.global().inject(this);
    setup();

    // Retrieve the FavoriteLocation
    int index = getIntent().getExtras().getInt("index");
    LocalUser localUser = app.getLocalUser();
    List<FavoriteLocation> favoriteLocations = localUser.getFavoriteLocations();

    // Just in case server changes favorite locations data after the activity launched
    if (index < favoriteLocations.size()) {
      FavoriteLocation location = favoriteLocations.get(index);
      bindData(location);
      bindEvents(index, location);
    }
  }

  /**
   * Setup up Activity page for Location Editing
   */
  private void setup() {
    setContentView(R.layout.activity_edit_location);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Setting custom font
    textLocationName = (TextView) findViewById(R.id.location_name);
    textSaveChange = (TextView) findViewById(R.id.save_changes_button);

    Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
    textLocationName.setTypeface(pierSans);
    textSaveChange.setTypeface(pierSans);
  }

  /**
   * Should save Location's address data
   * @param location
   */
  private void bindData(FavoriteLocation location) {
    textLocationName.setText(location.getName());
    //TODO: Address won't be saved!
  }

  /**
   * Handles all button clicks(bindings)
   * @param index To identify which location on the list
   * @param location To identify the location
   */
  private void bindEvents(int index, FavoriteLocation location) {
    // Clicking the back button takes you to the previous activity
    ImageButton backButton = (ImageButton) findViewById(R.id.btn_backarrow);
    Button saveButton = (Button) findViewById(R.id.save_changes_button);
    backButton.setOnClickListener(view -> finish());
    saveButton.setOnClickListener(view -> {
      location.setName(textLocationName.getText().toString());
      app.getLocalUser().setFavoriteLocation(index, location);
      finish();
    });
  }
}

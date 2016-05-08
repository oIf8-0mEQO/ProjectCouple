package group50.coupletones.controller.tab.favoritelocations;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import group50.coupletones.R;

public class EditLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Custom fonts
        TextView location_text = (TextView) findViewById(R.id.location_name);
        TextView address_text = (TextView) findViewById(R.id.location_address);
        TextView save_changes_text = (TextView) findViewById(R.id.save_changes_button);
        TextView delete_location_text = (TextView) findViewById(R.id.delete_location_button);

        Typeface pierSans = Typeface.createFromAsset(getAssets(), getString(R.string.pier_sans));
        location_text.setTypeface(pierSans);
        address_text.setTypeface(pierSans);
        save_changes_text.setTypeface(pierSans);
        delete_location_text.setTypeface(pierSans);



    }


}

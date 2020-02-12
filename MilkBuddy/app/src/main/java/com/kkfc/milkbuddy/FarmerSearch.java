package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

public class FarmerSearch extends AppCompatActivity {

    DatabaseHelper db;
    private SearchView farmerSearchView;
    private String searchBarQuery;
    private ListView farmerListView;
    SimpleCursorAdapter transporterCursorAdapter;
    private Spinner transportersSpinnerView;
    SimpleCursorAdapter farmerCursorAdapter;
    CheckBox active_checkbox;
    CheckBox collected_checkbox;
    int selectedDropdownRoute;

    // THE DESIRED COLUMNS TO BE BOUND
    final String[] farmerColumns = new String[]{
            db.FARMER_ID,
            db.FARMER_NAME,
            db.FARMER_EXPECTED_COLLECTION_TIME
    };

    // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
    final int[] farmerTo = new int[]{
            R.id.farmer_id,
            R.id.farmer_name,
            R.id.farmer_expected_collection_time
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_search);
        db = new DatabaseHelper(this);

        // By default select the option to see all routes (id = -1)
        selectedDropdownRoute = -1;
        searchBarQuery = "";
        farmerSearchView = findViewById(R.id.farmerSearchView);
        farmerSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                searchBarQuery = text;
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        text);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
                return false;
            }
        });

        // Make dropdown for transporters/routes
        String[] transporterAdapterCols=new String[]{"name"};
        int[] transporterAdapterRowViews=new int[]{android.R.id.text1};

        // Add an option to the dropdown to view all routes (rather than filtering by a single route)
        Cursor transporterCursor = db.fetchTransporters();
        MatrixCursor allRoutesOption = new MatrixCursor(new String[] { "_id", "name" });
        allRoutesOption.addRow(new String[] { "-1", "All Routes" });
        Cursor[] cursorsToMerge = { allRoutesOption, transporterCursor };
        Cursor routesDropdownCursor = new MergeCursor(cursorsToMerge);

        transporterCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, routesDropdownCursor, transporterAdapterCols, transporterAdapterRowViews,0);
        transporterCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportersSpinnerView = findViewById(R.id.Spinner1);
        transportersSpinnerView.setAdapter(transporterCursorAdapter);

        // TODO Finish this
        transportersSpinnerView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // When the dropdown selection changes, fetch the ID of the selected item
                Cursor cursor = ((SimpleCursorAdapter)parentView.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                selectedDropdownRoute = cursor.getInt(cursor.getColumnIndex("_id"));

                // TODO take this away
                Log.i("ID is", Integer.toString(selectedDropdownRoute));

                // Re-fetch farmers based on route selected from dropdown
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        // List farmers
        Cursor farmerCursor = db.fetchFarmers();
        farmerListView = findViewById(R.id.list_view);

        if(farmerCursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        } else {
            farmerCursorAdapter = new SimpleCursorAdapter(this, R.layout.farmer_list_entry, farmerCursor, farmerColumns, farmerTo, 0);
            farmerListView.setAdapter(farmerCursorAdapter);
        }

        active_checkbox = findViewById(R.id.checkBox1);
        collected_checkbox = findViewById(R.id.checkBox2);

        // Re-fetch farmers when checkbox is toggled
        active_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
            }
        });

        // Re-fetch farmers when checkbox is toggled
        collected_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
            }
        });

    }
}

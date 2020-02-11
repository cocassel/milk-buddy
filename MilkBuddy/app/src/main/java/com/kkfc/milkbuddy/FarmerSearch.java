package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import java.util.ArrayList;
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
                        // TODO change this
                        null,
                        text);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
                return false;
            }
        });


        // TODO: Change adapter type so we can get IDs
        // TODO: change to just name (not first and last name)
        // Make dropdown for transporters/routes
        String[] transporterAdapterCols=new String[]{"name"};
        int[] transporterAdapterRowViews=new int[]{android.R.id.text1};


        Cursor transporterCursor = db.fetchTransporters();


        MatrixCursor allRoutesOption = new MatrixCursor(new String[] { "_id", "name" });
        allRoutesOption.addRow(new String[] { "-1", "All Routes" });
        Cursor[] cursorsToMerge = { allRoutesOption, transporterCursor };
        Cursor routesDropdownCursor = new MergeCursor(cursorsToMerge);

        // Add an option to the dropdown to view all routes (rather than filtering by a single route)
        transporterCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, routesDropdownCursor, transporterAdapterCols, transporterAdapterRowViews,0);
        transporterCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportersSpinnerView = findViewById(R.id.Spinner1);
        transportersSpinnerView.setAdapter(transporterCursorAdapter);

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
                        // TODO change this
                        null,
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
                        // TODO change this
                        null,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
            }
        });

    }
}

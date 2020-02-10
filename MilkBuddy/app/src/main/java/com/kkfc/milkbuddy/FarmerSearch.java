package com.kkfc.milkbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FarmerSearch extends AppCompatActivity {

    DatabaseHelper db;
    private SearchView farmerSearchView;
    private String searchBarQuery;
    private ListView farmerListView;
    ArrayList<String> transporterListItems;
    ArrayAdapter transporterAdapter;
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
        // Make dropdown for transporters/routes
        Cursor transporterCursor = db.fetchTransporters();
        transporterListItems = new ArrayList<>();
        transporterListItems.add("All Routes");

        transportersSpinnerView = findViewById(R.id.Spinner1);

        if(transporterCursor.getCount() == 0) {

            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            while(transporterCursor.moveToNext()) {
                transporterListItems.add(transporterCursor.getString(1) + " " + transporterCursor.getString(2));
            }
            transporterAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, transporterListItems);
            transportersSpinnerView.setAdapter(transporterAdapter);
        }

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

        farmerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToFarmerCollection();
            }
        });

    }

    private void goToFarmerCollection() {
        Intent intent = new Intent(this, FarmerCollection.class);
        startActivity(intent);
    }
}

package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FarmerSearch extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> farmerListItems;
    ArrayAdapter farmerAdapter;
    private ListView farmerListView;
    ArrayList<String> transporterListItems;
    ArrayAdapter transporterAdapter;
    private Spinner transportersSpinnerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_search);
        db = new DatabaseHelper(this);

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
        farmerListItems = new ArrayList<>();
        farmerListView = findViewById(R.id.list_view);

        if(farmerCursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            while(farmerCursor.moveToNext()) {
                // 1 is the column for first name
                farmerListItems.add(farmerCursor.getString(2));
            }
            farmerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, farmerListItems);
            farmerListView.setAdapter(farmerAdapter);
        }

    }
}

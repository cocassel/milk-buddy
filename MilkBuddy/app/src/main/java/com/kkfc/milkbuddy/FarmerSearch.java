package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import java.util.ArrayList;
import android.widget.SimpleCursorAdapter;

public class FarmerSearch extends AppCompatActivity {

    DatabaseHelper db;
    private  SearchView farmerSearchView;
    private ListView farmerListView;
    ArrayList<String> transporterListItems;
    ArrayAdapter transporterAdapter;
    private Spinner transportersSpinnerView;
    SimpleCursorAdapter farmerCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_search);
        db = new DatabaseHelper(this);


//        // TODO MAKE THIS WORK
//        farmerSearchView = findViewById(R.id.farmerSearchView);
//        farmerSearchView.setOnQueryTextListener(new OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String text) {
//
//                // TODO Auto-generated method stub
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
//
//                farmerCursorAdapter.getFilter().filter(text);
//                return false;
//            }
//        });


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
            // THE DESIRED COLUMNS TO BE BOUND
            String[] columns = new String[]{
                    db.FARMER_ID,
                    db.FARMER_ASSIGNED_TRANSPORTER_ID,
                    db.FARMER_NAME,
                    db.FARMER_ACTIVE,
                    db.FARMER_EXPECTED_COLLECTION_TIME
            };

            // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
            int[] to = new int[]{
                    R.id.farmer_id,
                    R.id.farmer_assigned_transporter_id,
                    R.id.farmer_name,
                    R.id.farmer_active,
                    R.id.farmer_expected_collection_time
            };
            farmerCursorAdapter = new SimpleCursorAdapter(this, R.layout.farmer_list_entry, farmerCursor, columns, to, 0);
            farmerListView.setAdapter(farmerCursorAdapter);
        }

    }
}

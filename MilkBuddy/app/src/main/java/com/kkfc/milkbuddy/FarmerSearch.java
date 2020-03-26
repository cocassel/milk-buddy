package com.kkfc.milkbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerSearch extends AppCompatActivity {

    DatabaseHelper db;
    private Button resetButton;
    private Button dropOffToReceiverButton;
    AlertDialog.Builder builder;
    private SearchView farmerSearchView;
    private String searchBarQuery;
    private ListView farmerListView;
    SimpleCursorAdapter transporterCursorAdapter;
    private Spinner transportersSpinnerView;
    SimpleCursorAdapter farmerCursorAdapter;
    CheckBox active_checkbox;
    CheckBox collected_checkbox;
    int selectedDropdownRoute;
    SharedPreferences states;

    // THE DESIRED COLUMNS TO BE BOUND
    final String[] farmerColumns = new String[]{
            db.FARMER_NAME,
            db.FARMER_EXPECTED_COLLECTION_TIME
    };

    // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
    final int[] farmerTo = new int[]{
            R.id.farmer_name,
            R.id.farmer_expected_collection_time
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_search);

        db = new DatabaseHelper(this);

        // Default for dropdown is to see all routes (id = -1)
        selectedDropdownRoute = -1;
        // Default for search bary query should be empty string
        searchBarQuery = "";

        // Retrieve saved state of checkboxes, dropdown, and search bar. This will be used to set them accordingly
        states = getSharedPreferences("states", Context.MODE_PRIVATE);

        // Set search bar query
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
                saveState();
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

        // Get prior dropdown selection (if applicable)
        int selectedDropdownPosition = states.getInt("selectedDropdownPosition", -1);

        //Set dropdown selection
        transportersSpinnerView.setSelection(selectedDropdownPosition);


        transportersSpinnerView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // When the dropdown selection changes, fetch the ID of the selected item
                Cursor cursor = ((SimpleCursorAdapter)parentView.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                selectedDropdownRoute = cursor.getInt(cursor.getColumnIndex("_id"));

                // Re-fetch farmers based on route selected from dropdown
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
                saveState();
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

        // Set state of checkboxes based on prior selections
        boolean isActiveChecked = states.getBoolean("activeCheckbox", false);
        boolean isCollectedChecked = states.getBoolean("collectedCheckbox", false);
        active_checkbox.setChecked(isActiveChecked);
        collected_checkbox.setChecked(isCollectedChecked);

        // Re-fetch farmers when checkbox is toggled and save state
        active_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
                saveState();
            }
        });

        // Re-fetch farmers when checkbox is toggled and save state
        collected_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor newFarmerCursor = db.fetchFarmers(
                        active_checkbox.isChecked(),
                        collected_checkbox.isChecked(),
                        selectedDropdownRoute,
                        searchBarQuery);
                farmerCursorAdapter.changeCursor(newFarmerCursor);
                saveState();
            }
        });

        farmerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                int selectedFarmerId = cursor.getInt(cursor.getColumnIndex("_id"));
                String selectedFarmerName =cursor.getString(cursor.getColumnIndex("name"));
                goToFarmerCollection(selectedFarmerId, selectedFarmerName);
            }
        });

        // Button for dropping off to receiver
        dropOffToReceiverButton = findViewById(R.id.button1);
        dropOffToReceiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                Toast.makeText(getApplicationContext(),"Switching to receiver login",
                                        Toast.LENGTH_SHORT).show();
                                goToReceiverLogin();
                            }
        });

        // Button for resetting the app
        resetButton = findViewById(R.id.button2);
        builder = new AlertDialog.Builder(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to reset Milk Buddy? This will clear your collection data.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Resetting the application",
                                        Toast.LENGTH_SHORT).show();
                                db.resetTables();
                                clearState();
                                // Redirect to import transporters page
                                goToImportTransporters();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Cancelling application reset",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Milk Buddy");
                alert.show();
            }

        });

    }

    // This function saves the state of the checkboxes, dropdown, and search bar. The idea is that after
    // a transporter saves collection information and returns to the farmer search page, the transporter
    // should not need to redo their preferences. If the checkboxes were checked before, they should
    // stay checked. The search bar query should also remain. The dropdown should also keep its selection
    private void saveState() {
        states = getSharedPreferences("states", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = states.edit();
        // Save state of both checkboxes and dropdown position
        // We purposely do not save the state of the search bar
        editor.putBoolean("activeCheckbox", active_checkbox.isChecked());
        editor.putBoolean("collectedCheckbox", collected_checkbox.isChecked());
        editor.putInt("selectedDropdownPosition", transportersSpinnerView.getSelectedItemPosition());
        editor.commit();
    }

    // Clear state of farmer search page (checkboxes, search bar, dropdown)
    // Also clear state of farmer collection page (container dropdown)
    private void clearState() {
        SharedPreferences states = getSharedPreferences("states", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = states.edit();
        editor.clear().commit();
    }

    private void goToFarmerCollection(int selectedFarmerId, String selectedFarmerName) {
        Intent intent = new Intent(this, FarmerCollection.class);
        intent.putExtra("farmerId", selectedFarmerId);
        intent.putExtra("farmerName",selectedFarmerName);
        startActivity(intent);
    }


    private void goToImportTransporters() {
        Intent intent = new Intent(this, ImportTransporters.class);
        startActivity(intent);
    }


    private void goToReceiverLogin(){
        Intent intent = new Intent(this,ReceiverLogin.class );
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // We intentionally do not allow the user to go back from this page. Once the transporter has
        // finished the set-up phase (importing, logging-in, and choosing containers) they may not
        // go back to it unless they reset the app.
    }

}

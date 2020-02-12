package com.kkfc.milkbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class TransporterLogin extends AppCompatActivity {

    DatabaseHelper db;
    private ListView transporterListView;
    SimpleCursorAdapter transporterCursorAdapter;
    int selectedTransporter;

    // THE DESIRED COLUMNS TO BE BOUND
    final String[] transporterColumns = new String[]{ db.TRANSPORTER_NAME };

    // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
    final int[] transporterTo = new int[]{ R.id.transporter_name };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_login);
        db = new DatabaseHelper(this);

        // List transporters
        Cursor transporterCursor = db.fetchTransporters();
        transporterListView = findViewById(R.id.list_view);

        if(transporterCursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        } else {
            transporterCursorAdapter = new SimpleCursorAdapter(this, R.layout.transporter_list_entry, transporterCursor, transporterColumns, transporterTo, 0);
            transporterListView.setAdapter(transporterCursorAdapter);
        }

        transporterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToTransporterHomepage();

                // When a transporter is selected, fetch the ID of the selected transporter
                Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                selectedTransporter = cursor.getInt(cursor.getColumnIndex("_id"));

                // TODO: Save logged-in transporter to database

                //Log.i("ID is", Integer.toString(selectedTransporter));
            }
        });

    }
    private void goToTransporterHomepage() {
        Intent intent = new Intent(this, TransporterContainerSelection.class);
        startActivity(intent);
    }
}

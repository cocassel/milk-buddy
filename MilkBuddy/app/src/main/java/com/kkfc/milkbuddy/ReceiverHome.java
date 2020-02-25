package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiverHome extends AppCompatActivity {

    DatabaseHelper db;
    private TextView NAME;
    private ListView containerListView;
    SimpleCursorAdapter containerCursorAdapter;

    // THE DESIRED COLUMNS TO BE BOUND
   // final String[] containerColumns = new String[]{ db.CONTAINER_ID };

    // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
    //final int[] containerTo = new int[]{ R.id.CONTAINER_ID};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home);

        NAME = (TextView) findViewById(R.id.transporterName);

        db = new DatabaseHelper(this);

        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        Log.i("logged in", loggedInTransporter);
        NAME= (TextView)findViewById(R.id.transporterName);
        NAME.setText("These are  " + loggedInTransporter + "'s containers");
        


       /* // List containers
        Cursor containerCursor = db.fetchContainers();
        containerListView = findViewById(R.id.list_view);

        if (containerCursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        } else {
            containerCursorAdapter = new SimpleCursorAdapter(this, R.layout.transporter_list_entry, containerCursor, containerColumns, containerTo, 0);
            containerListView.setAdapter(containerCursorAdapter);
        }*/
    }



        /*transporterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToTransporterHomepage();

                // When a transporter is selected, fetch the ID of the selected transporter
                Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                int selectedTransporterId = cursor.getInt(cursor.getColumnIndex(db.TRANSPORTER_ID));
                String selectedTransporterName = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
                String selectedTransporterPhoneNumber = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_PHONE_NUMBER));

                // Save logged-in transporter to database
                db.insertLoggedInTransporter(selectedTransporterId, selectedTransporterName, selectedTransporterPhoneNumber);
            }
        });*/




    @Override
    public void onBackPressed() {
        // TODO
    }
}

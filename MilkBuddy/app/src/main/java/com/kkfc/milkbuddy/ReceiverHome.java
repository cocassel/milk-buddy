package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiverHome extends AppCompatActivity {

    DatabaseHelper db;
    private TextView NAME;
    private TextView receiverName;
    private ListView containerListView;
    private Button exportButton;
    private Button test;
    SimpleCursorAdapter containerCursorAdapter;

    // THE DESIRED COLUMNS TO BE BOUND
    String[] containerColumns = new String[]{
            "container_info"
            //db.CONTAINER_ID,
            //db.CONTAINER_SIZE
    };

    // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
   int[] containerTo = new int[]{
            R.id.container_id,
            //R.id.container_size
    };

    //String [] containerColumns = new String[]{"container_dropdown"};
    //int[] containerTo =new int[]{android.R.id.list};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home);


        NAME = (TextView) findViewById(R.id.transporterName);
        receiverName = (TextView) findViewById(R.id.receiverName);

        db = new DatabaseHelper(this);

        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        Log.i("logged in", loggedInTransporter);
        NAME= (TextView)findViewById(R.id.transporterName);
        NAME.setText("These are " + loggedInTransporter + "'s containers");

        //Fetch the loggedInReciever to show on the UI
        Cursor c = db.fetchLoggedInReceiver();
        //only one row in the table so use the first row
        c.moveToFirst();
        String loggedInReceiver = c.getString(c.getColumnIndex(db.RECEIVER_NAME));
        Log.i("logged in", loggedInReceiver);
        receiverName = (TextView)findViewById(R.id.receiverName);
        receiverName.setText("Hello " + loggedInReceiver + "!");



       /* exportButton = findViewById(R.id.button1);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData();
            }

        });*/


        // List transporters
        Cursor containerCursor = db.fetchConcatContainerForReceiver();
        containerListView = findViewById(R.id.list_view);

        if(containerCursor.getCount() == 0) {
            exportData();
        } else {
            containerCursorAdapter = new SimpleCursorAdapter(this, R.layout.container_list_entry, containerCursor, containerColumns, containerTo, 0);
            containerListView.setAdapter(containerCursorAdapter);

        }


        containerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                int selectedContainerId = cursor.getInt(cursor.getColumnIndex(db.CONTAINER_ID));
                String selectedContainerSize =cursor.getString(cursor.getColumnIndex(db.CONTAINER_SIZE));
                String selectedContainerAmtRemaining =cursor.getString(cursor.getColumnIndex(db.CONTAINER_AMOUNT_REMAINING));
                goToReceiverCollection(selectedContainerId, selectedContainerSize, selectedContainerAmtRemaining);
            }
        });

    }


    private void exportData() {
        Intent intent = new Intent(this, ExportTransporterData.class);
        startActivity(intent);
    }

    private void goToReceiverCollection(int selectedContainerId, String selectedContainerSize, String selectedContainerAmtRemaining) {
        Intent intent = new Intent(this, ReceiverCollection.class);
        intent.putExtra("containerId", selectedContainerId);
        intent.putExtra("containerSize",selectedContainerSize);
        intent.putExtra("containerAmtRemaining",selectedContainerAmtRemaining);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        // Once a receiver has logged in, the farmer search/collection page and receiver login pages
        // are no longer accessible so we disable the back button (i.e. it does nothing).
    }
}

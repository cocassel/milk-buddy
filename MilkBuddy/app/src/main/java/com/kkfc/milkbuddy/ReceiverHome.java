package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ReceiverHome extends AppCompatActivity {

    DatabaseHelper db;
    private TextView NAME;
    private ListView containerListView;
    private Button exportButton;
    SimpleCursorAdapter containerCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home);

        // TODO: Automatically redirect to export page if all containers are received

        NAME = (TextView) findViewById(R.id.transporterName);

        db = new DatabaseHelper(this);

        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        Log.i("logged in", loggedInTransporter);
        NAME= (TextView)findViewById(R.id.transporterName);
        NAME.setText("These are " + loggedInTransporter + "'s containers");

        exportButton = findViewById(R.id.button1);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData();
            }
        });

    }

    private void exportData() {
        Intent intent = new Intent(this, ExportTransporterData.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        // Once a receiver has logged in, the farmer search/collection page and receiver login pages
        // are no longer accessible so we disable the back button (i.e. it does nothing).
    }
}

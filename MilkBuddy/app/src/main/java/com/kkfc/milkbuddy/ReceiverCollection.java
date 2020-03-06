package com.kkfc.milkbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ReceiverCollection extends AppCompatActivity {

    DatabaseHelper db;
    AlertDialog.Builder builder;
    private Button cancelCollection;
    private Button saveCollection;
    private Button transporterTextView;
    private TextView containerTextView;
    private EditText quantity;
    private EditText quantityEmpty;
    private int transporterId;
    private int containerId;
    private int receiverId;
    private RadioButton sniffPass, sniffFail, sniffNa, alcoholPass, alcoholFail, alcoholNa,densityTwoSeven, densityTwoEight, densityTwoNine, densityThirty, densityThirtyOnePlus, densityNa ;
    private String sniffTest;
    private String alcoholTest;
    private String densityTest;
    private String dateToday;
    private String timeToday;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_collection);

        db = new DatabaseHelper(this);
        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        transporterId = cursor.getInt(cursor.getColumnIndex(db.TRANSPORTER_ID));
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        transporterTextView = findViewById(R.id.textView1);
        transporterTextView.setText("Transporter: " );



        // Cancel Collection Process
        cancelCollection = findViewById(R.id.Button01);
        builder = new AlertDialog.Builder(this);
        cancelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clicking the cancel button should do the same thing as clicking the back button
                onBackPressed();
            }
        });


        // Save Collection Process



    }

    private void returnToReceiverHome() {
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        builder.setMessage("All unsaved data will be lost. Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Collection Canceled",
                                Toast.LENGTH_SHORT).show();
                        returnToReceiverHome();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Cancel Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }
}

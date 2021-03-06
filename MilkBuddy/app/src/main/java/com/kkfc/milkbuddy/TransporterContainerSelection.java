package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;




import java.util.ArrayList;

public class TransporterContainerSelection extends AppCompatActivity {

    Context context = this;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    AlertDialog.Builder builder;
    private Button nextButton;
    int containerSum;
    int minteger20 = 0;
    int minteger25 = 0;
    int minteger30 = 0;
    int minteger35 = 0;
    int minteger40 = 0;
    int minteger45 = 0;
    int minteger50 = 0;

    private TextView NAME;
    private SimpleCursorAdapter transporterCursorAdapter;
    String transporterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_container_selection);
        NAME= (TextView)findViewById(R.id.transporterNameView);

        db = new DatabaseHelper(this);

        //Fetch the loggedInTransporter to show on the UI
        Cursor cursor = db.fetchLoggedInTransporter();
        //only one row in the table so use first row
        cursor.moveToFirst();
        String loggedInTransporter = cursor.getString(cursor.getColumnIndex(db.TRANSPORTER_NAME));
        Log.i("logged in", loggedInTransporter);
        NAME= (TextView)findViewById(R.id.transporterNameView);
        NAME.setText("Hello " + loggedInTransporter + "!");


        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerPopup();
            }
        });



    }

    private void goToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }

    //20 L
    public void increaseInteger20(View view) {
        minteger20 = minteger20 + 1;
        if(minteger20>10){
            minteger20=10;
        }
        updateContainerSum();
        display20(minteger20);
    }

    public void decreaseInteger20(View view) {
        minteger20 = minteger20 - 1;
        if(minteger20<0){
            minteger20=0;
        }
        updateContainerSum();
        display20(minteger20);
    }

    private void display20(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }

    //25 L
    public void increaseInteger25(View view) {
        minteger25 = minteger25 + 1;
        if(minteger25>10){
            minteger25=10;
        }
        updateContainerSum();
        display25(minteger25);

    }public void decreaseInteger25(View view) {
        minteger25 = minteger25 - 1;
        if(minteger25<0){
            minteger25=0;
        }
        updateContainerSum();
        display25(minteger25);
    }

    private void display25(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number25);
        displayInteger.setText("" + number);
    }

    //30 L
    public void increaseInteger30(View view) {
        minteger30 = minteger30 + 1;
        if(minteger30>10){
            minteger30=10;
        }
        updateContainerSum();
        display30(minteger30);

    }public void decreaseInteger30(View view) {
        minteger30 = minteger30 - 1;
        if(minteger30<0){
            minteger30=0;
        }
        updateContainerSum();
        display30(minteger30);
    }

    private void display30(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number30);
        displayInteger.setText("" + number);

    }

    //35 L
    public void increaseInteger35(View view) {
        minteger35 = minteger35 + 1;
        if(minteger35>10){
            minteger35=10;
        }
        updateContainerSum();
        display35(minteger35);
    }

    public void decreaseInteger35(View view) {
        minteger35 = minteger35 - 1;
        if(minteger35<0){
            minteger35=0;
        }
        updateContainerSum();
        display35(minteger35);
    }

    private void display35(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number35);
        displayInteger.setText("" + number);
    }

    //40 L
    public void increaseInteger40(View view) {
        minteger40 = minteger40 + 1;
        if(minteger40>10){
            minteger40=10;
        }
        updateContainerSum();
        display40(minteger40);

    }public void decreaseInteger40(View view) {
        minteger40 = minteger40 - 1;
        if(minteger40<0){
            minteger40=0;
        }
        updateContainerSum();
        display40(minteger40);
    }

    private void display40(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number40);
        displayInteger.setText("" + number);
    }

    //45 L
    public void increaseInteger45(View view) {
        minteger45 = minteger45 + 1;
        if(minteger45>10){
            minteger45=10;
        }
        updateContainerSum();
        display45(minteger45);

    }public void decreaseInteger45(View view) {
        minteger45 = minteger45 - 1;
        if(minteger45<0){
            minteger45=0;
        }
        updateContainerSum();
        display45(minteger45);
    }

    private void display45(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number45);
        displayInteger.setText("" + number);
    }

    //50 L
    public void increaseInteger50(View view) {
        minteger50 = minteger50 + 1;
        if(minteger50>10){
            minteger50=10;
        }
        updateContainerSum();
        display50(minteger50);
    }

    public void decreaseInteger50(View view) {
        minteger50 = minteger50 - 1;
        if(minteger50<0){
            minteger50=0;
        }
        updateContainerSum();
        display50(minteger50);
    }

    private void display50(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number50);
        displayInteger.setText("" + number);
    }

    private void updateContainerSum() {
        containerSum = minteger20 + minteger25 + minteger30 + minteger35 + minteger40 + minteger45 + minteger50;
    }

    public void containerPopup() {
        builder = new AlertDialog.Builder(this);
        if (containerSum > 0) {
            builder.setMessage("You have selected " + containerSum + " containers. Are you sure you want to continue?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                db.addContainers(minteger20, 20);
                                db.addContainers(minteger25, 25);
                                db.addContainers(minteger30, 30);
                                db.addContainers(minteger35, 35);
                                db.addContainers(minteger40, 40);
                                db.addContainers(minteger45, 45);
                                db.addContainers(minteger50, 50);
                                Toast.makeText(TransporterContainerSelection.this, "Containers have been saved!", Toast.LENGTH_LONG).show();
                                goToFarmerSearch();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(TransporterContainerSelection.this, "Error saving containers!", Toast.LENGTH_LONG).show();
                            }
                            goToFarmerSearch();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Milk Buddy");
            alert.show();

        } else {
            builder.setMessage("Please select at least one container")
                    .setCancelable(false)
                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Milk Buddy");
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        // We will allow the user to go back to the transporter login page and select a different
        // transporter. When that happens, we will simply erase the existing data from the logged-in
        // transporter table and reenter the new transporter selected
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
        // When the user clicks the back button, they are navigated back to the transporter login.
        // Since they will need to re-log-in to proceed, we can safely delete the prior selection
        // Deleting this is necessary because if we leave the prior entry in the table and the user
        // keeps clicking the back button until they reach the import transporters page (first page
        // of app), the state check will go through and re-direct the user to the container page since
        // the state of the app will technically be logged-in transporter with no container selection
        db.deleteLoggedInTransporter();
    }


}




package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_container_selection);

        db = new DatabaseHelper(this);

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            containerPopup();
        //  goToFarmerSearch();
        }
         });

       // insertContainer();
        

    }

    private void goToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }

    //20 L

    public void increaseInteger(View view) {
        minteger20 = minteger20 + 1;
        if(minteger20>10){
            minteger20=10;
        }
        display(minteger20);

    }public void decreaseInteger(View view) {
        minteger20 = minteger20 - 1;
        if(minteger20<0){
            minteger20=0;
        }
        display(minteger20);
    }

    private void display(int number) {
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
        display25(minteger25);

    }public void decreaseInteger25(View view) {
        minteger25 = minteger25 - 1;
        if(minteger25<0){
            minteger25=0;
        }
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
        display30(minteger30);

    }public void decreaseInteger30(View view) {
        minteger30 = minteger30 - 1;
        if(minteger30<0){
            minteger30=0;
        }
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
        display35(minteger35);

    }public void decreaseInteger35(View view) {
        minteger35 = minteger35 - 1;
        if(minteger35<0){
            minteger35=0;
        }
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
        display40(minteger40);

    }public void decreaseInteger40(View view) {
        minteger40 = minteger40 - 1;
        if(minteger40<0){
            minteger40=0;
        }
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
        display45(minteger45);

    }public void decreaseInteger45(View view) {
        minteger45 = minteger45 - 1;
        if(minteger45<0){
            minteger45=0;
        }
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
        display50(minteger50);

    }public void decreaseInteger50(View view) {
        minteger50 = minteger50 - 1;
        if(minteger50<0){
            minteger50=0;
        }
        display50(minteger50);
    }

    private void display50(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number50);
        displayInteger.setText("" + number);

    }

    public void containerPopup() {
        containerSum = minteger20 + minteger25 + minteger30 + minteger35 + minteger40 + minteger45 + minteger50;
        builder = new AlertDialog.Builder(this);
        if (containerSum > 0) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                    //Toast.makeText(getApplicationContext(), "Switching to farmer search page",
                                      //      Toast.LENGTH_SHORT).show();
                                    //insertContainer();
                                    goToFarmerSearch();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Returning to container page",
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
        } else {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage("Please select at least one container")
                            .setCancelable(false)
                            .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Cancelling drop-off",
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
    }



    //update database with container information
  /*  public void insertContainer(){
            nextButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
                        }
                    }
            );


    } */




    @Override
    public void onBackPressed() {
        // TODO
    }


}




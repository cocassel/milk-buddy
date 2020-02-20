package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_container_selection);

        db = new DatabaseHelper(this);

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFarmerSearch();
            }
        });
        insertContainer();
    }

    private void goToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }

    //20 L
    int minteger20 = 0;
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
    int minteger25 = 0;
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
    int minteger30 = 0;
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
    int minteger35 = 0;
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
    int minteger40 = 0;
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
    int minteger45 = 0;
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
    int minteger50 = 0;
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

    //update database with container information

    public void insertContainer(){
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        try {
                            db.addContainers(minteger20, 20);
                            db.addContainers(minteger25, 25);
                            db.addContainers(minteger30, 30);
                            db.addContainers(minteger35, 35);
                            db.addContainers(minteger40, 40);
                            db.addContainers(minteger45, 45);
                            db.addContainers(minteger50, 50);
                            Toast.makeText(TransporterContainerSelection.this,"Containers have been saved!",Toast.LENGTH_LONG).show();
                            goToFarmerSearch();

                        }
                        catch(Exception e) {
                            e.printStackTrace();
                            Toast.makeText(TransporterContainerSelection.this, "Error saving containers!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    @Override
    public void onBackPressed() {
        // TODO
    }


}




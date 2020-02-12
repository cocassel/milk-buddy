package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class TransporterContainerSelection extends AppCompatActivity {

    DatabaseHelper db;
    private Button nextButton;

    int minteger = 0;
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
    }
    private void goToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }
    //20 L
    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);

    }public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
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
        display25(minteger25);

    }public void decreaseInteger25(View view) {
        minteger25 = minteger25 - 1;
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
        display30(minteger30);

    }public void decreaseInteger30(View view) {
        minteger30 = minteger30 - 1;
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
        display35(minteger35);

    }public void decreaseInteger35(View view) {
        minteger35 = minteger35 - 1;
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
        display40(minteger40);

    }public void decreaseInteger40(View view) {
        minteger40 = minteger40 - 1;
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
        display45(minteger45);

    }public void decreaseInteger45(View view) {
        minteger45 = minteger45 - 1;
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
        display50(minteger50);

    }public void decreaseInteger50(View view) {
        minteger50 = minteger50 - 1;
        display50(minteger50);
    }

    private void display50(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number50);
        displayInteger.setText("" + number);

    }


}




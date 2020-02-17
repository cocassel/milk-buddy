package com.kkfc.milkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerCollection extends AppCompatActivity {


    private Button cancelCollection;
    private Button saveCollection;
    private int farmerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_collection);

        Intent intent = getIntent();
        // Get the farmer ID, which was passed from the farmer search activity page
        farmerId = intent.getIntExtra("farmerId", 1);

        //Log.i("Farmer ID is", Integer.toString(farmerId));

        cancelCollection = findViewById(R.id.Button01);
        cancelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToFarmerSearch();
            }
        });

        saveCollection = findViewById(R.id.Button02);
        saveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save data into database
                returnToFarmerSearch();
            }
        });

    }

    private void returnToFarmerSearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // TODO
    }
}

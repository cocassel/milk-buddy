package com.kkfc.milkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerCollection extends AppCompatActivity {


    private Button cancelcollection;

    private Button savecollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_collection);

        cancelcollection = findViewById(R.id.Button1);
        cancelcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returntofarmersearch();
            }
        });

        savecollection = findViewById(R.id.Button2);
        savecollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data into database
                returntofarmersearch();
            }
        });


    }

    private void returntofarmersearch() {
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }
}

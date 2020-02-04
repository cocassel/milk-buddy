package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TransporterHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_homepage);


        Spinner mySpinner = (Spinner) findViewById(R.id.Spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TransporterHomepage.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.routeDropdown));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
}

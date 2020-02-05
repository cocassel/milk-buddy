package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TransporterHomepage extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> listItems;
    ArrayAdapter adapter;
    private Spinner spinnerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_homepage);

        db = new DatabaseHelper(this);

        Cursor cursor = db.fetchTransporters();
        listItems = new ArrayList<>();

        spinnerView = (Spinner) findViewById(R.id.Spinner1);

        if(cursor.getCount() == 0) {

            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext()) {
                listItems.add(cursor.getString(1) + " " + cursor.getString(2));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listItems);
            spinnerView.setAdapter(adapter);
        }

//        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TransporterHomepage.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.routeDropdown));
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerView.setAdapter(myAdapter);
    }
}

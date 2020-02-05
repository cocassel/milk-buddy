package com.kkfc.milkbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TransporterLogin extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> listItems;
    ArrayAdapter adapter;
    private ListView transporterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_login);
        db = new DatabaseHelper(this);

        Cursor cursor = db.fetchTransporters();
        listItems = new ArrayList<>();
        transporterListView = findViewById(R.id.list_view);

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext()) {
                // 1 is the column for first name
                listItems.add(cursor.getString(1) + " " + cursor.getString(2));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
            transporterListView.setAdapter(adapter);
        }

        transporterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TransporterLogin.this, listItems.get(position), Toast.LENGTH_SHORT).show();
                goToTransporterHomepage();
            }                                                                     
        });


    }
    private void goToTransporterHomepage() {
        Intent intent = new Intent(this, TransporterHomepage.class);
        startActivity(intent);
    }
}

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
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private ListView transporterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_login);
        db = new DatabaseHelper(this);

        Cursor cursor = db.fetchTransporters();
        listItem = new ArrayList<>();
        transporterListView = findViewById(R.id.list_view);

        if(cursor.getCount() == 0) {

            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            StringBuffer buffer = new StringBuffer();
            while(cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            transporterListView.setAdapter(adapter);
        }

        transporterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TransporterLogin.this, listItem.get(position), Toast.LENGTH_SHORT).show();
                gototransporterhomepage();
            }                                                                     
        });







    }
    private void gototransporterhomepage() {
        Intent intent = new Intent(this, TransporterHomepage.class);
        startActivity(intent);
    }
}

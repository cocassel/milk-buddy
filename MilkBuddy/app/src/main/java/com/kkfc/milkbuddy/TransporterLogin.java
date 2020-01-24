package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        Cursor cursor = db.fetchUsers();
        listItem = new ArrayList<>();
        transporterListView = findViewById(R.id.list_view);

        if(cursor.getCount() == 0) {

            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else {
            StringBuffer buffer = new StringBuffer();
            while(cursor.moveToNext()) {
                // TODO check user type for transporter
                listItem.add(cursor.getString(4));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            transporterListView.setAdapter(adapter);
        }
    }
}

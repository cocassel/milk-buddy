package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    private Button start;
    private Button start2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        start = findViewById(R.id.button1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importNewData();
            }
        });
        start2 = findViewById(R.id.button2);
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueWithExistingData();
            }
        });

    }

    private void importNewData() {
        db.insertTransportersFromCSV();
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }

    private void continueWithExistingData() {
        // TODO make sure tables are wiped properly
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }


}

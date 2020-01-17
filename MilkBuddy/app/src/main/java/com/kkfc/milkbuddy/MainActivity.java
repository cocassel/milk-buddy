package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private Button trnsp;
    private Button Gsttrnsp;
    private Button reclog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        trnsp = findViewById(R.id.button4);
        trnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trnsplogin();
            }
        });
        Gsttrnsp = findViewById(R.id.button5);
        Gsttrnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsttrnsplogin();
            }
        });
        reclog = findViewById(R.id.button7);
        reclog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recverlog();
            }
        });
    }

    private void trnsplogin() {
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
    }

    private void gsttrnsplogin() {
        Intent intent = new Intent(this, GuestTransporter.class);
        startActivity(intent);
    }

    private void recverlog() {
        Intent intent = new Intent(this, ReceiverLog.class);
        startActivity(intent);
    }
}

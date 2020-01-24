package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    DatabaseHelper db;
    private Button transporter;
    private Button guestTransporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        DatabaseHelper db = new DatabaseHelper(this);

        transporter = findViewById(R.id.button1);
        transporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transporterLogin();
            }
        });
        guestTransporter = findViewById(R.id.button2);
        guestTransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestTransporterLogin();
            }
        });
    }

    private void transporterLogin() {
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
    }

    private void guestTransporterLogin() {
        Intent intent = new Intent(this, GuestTransporter.class);
        startActivity(intent);
    }


}


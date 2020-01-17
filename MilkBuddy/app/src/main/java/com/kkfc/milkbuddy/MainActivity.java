package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    private Button transporter;
    private Button guestTransporter;
    private Button receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

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
        receiver = findViewById(R.id.button3);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiverLogin();
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

    private void receiverLogin() {
        Intent intent = new Intent(this, ReceiverLogin.class);
        startActivity(intent);
    }
}

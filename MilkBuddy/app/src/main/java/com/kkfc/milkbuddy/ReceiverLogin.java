package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiverLogin extends AppCompatActivity {

    DatabaseHelper db;
    private Button loginReceiver;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_login);

        db = new DatabaseHelper(this);

        cancelButton = findViewById(R.id.Button01);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pressing the cancel button does the same thing as pressing the back button
                onBackPressed();
            }
        });

        loginReceiver = findViewById(R.id.Button02);
        loginReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO show popup if empty fields and disallow moving forward
                goToReceiverHomepage();
            }
        });
    }

    private void goToReceiverHomepage() {
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // We allow the user to go back to farmer search/collection as long as no receiver is
        // logged-in yet. Once a receiver logs in, farmer search/collection will no longer be accessible
        Intent intent = new Intent(this, FarmerSearch.class);
        startActivity(intent);
    }
}

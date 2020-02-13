package com.kkfc.milkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GuestTransporter extends AppCompatActivity {

    private Button cancelguesttransporter;
    private Button loginguesttransporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_transporter);

        cancelguesttransporter = findViewById(R.id.Button01);
        cancelguesttransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackTransporterLogin();
            }
        });

        loginguesttransporter = findViewById(R.id.Button02);
        loginguesttransporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTransporterHomepage();
            }
        });


    }

    private void goToTransporterHomepage() {
        Intent intent = new Intent(this, TransporterContainerSelection.class);
        startActivity(intent);
    }

    private void goBackTransporterLogin() {
        Intent intent = new Intent(this, TransporterLogin.class);
        startActivity(intent);
    }

}

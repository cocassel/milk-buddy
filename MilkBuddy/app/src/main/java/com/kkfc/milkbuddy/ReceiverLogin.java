package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiverLogin extends AppCompatActivity {

    DatabaseHelper db;
    private Button loginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_login);

        db = new DatabaseHelper(this);

        loginReceiver = findViewById(R.id.Button02);
        loginReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // TODO
    }
}

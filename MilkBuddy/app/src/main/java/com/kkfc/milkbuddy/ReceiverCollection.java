package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ReceiverCollection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_collection);
    }

    @Override
    public void onBackPressed() {
        // TODO add warning/pop-up before going back. Also use this function for the cancel button
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }
}

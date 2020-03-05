package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ReceiverCollection extends AppCompatActivity {

    AlertDialog.Builder builder;
    private Button cancelCollection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_collection);

        // Cancel Collection Process
        cancelCollection = findViewById(R.id.Button01);
        builder = new AlertDialog.Builder(this);
        cancelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clicking the cancel button should do the same thing as clicking the back button
                onBackPressed();
            }
        });
    }

    private void returnToReceiverHome() {
        Intent intent = new Intent(this, ReceiverHome.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        builder.setMessage("All unsaved data will be lost. Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Collection Canceled",
                                Toast.LENGTH_SHORT).show();
                        returnToReceiverHome();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Cancel Aborted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Milk Buddy");
        alert.show();
    }
}

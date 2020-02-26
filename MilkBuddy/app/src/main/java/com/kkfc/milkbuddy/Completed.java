package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Completed extends AppCompatActivity {

    DatabaseHelper db;
    private Button resetButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        db = new DatabaseHelper(this);

        resetButton = findViewById(R.id.button1);
        builder = new AlertDialog.Builder(this);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This ends the cycle so we want to reset the app and redirect to homepage

                builder.setMessage("Are you sure you want to reset Milk Buddy? You will not be able to export your data again once you reset the app.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Resetting the application",
                                        Toast.LENGTH_SHORT).show();
                                db.resetTables();
                                // Redirect to import transporters page
                                goToImportTransporters();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Cancelling application reset",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Milk Buddy");
                alert.show();
            }
        });


    }

    private void goToImportTransporters() {
        Intent intent = new Intent(this, ImportTransporters.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Allow a user to go back to the ExportPlantData page so that they can export again
        // if they so choose
        Intent intent = new Intent(this, ExportPlantData.class);
        startActivity(intent);
    }
}

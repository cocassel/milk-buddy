package com.kkfc.milkbuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class ExportTransporterData extends AppCompatActivity {

    DatabaseHelper db;
    private static final int WRITE_REQUEST_CODE = 42;
    private Button exportButton;
    private Button skipButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_transporter_data);

        db = new DatabaseHelper(this);

        exportButton = findViewById(R.id.button1);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new document of type csv
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT)
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("text/csv")
                        .putExtra(Intent.EXTRA_TITLE, "Transporter Milk Log " + date);
                startActivityForResult(intent, WRITE_REQUEST_CODE);
            }
        });

        skipButton = findViewById(R.id.button2);
        builder = new AlertDialog.Builder(this);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to skip exporting transporter collection data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goToExportPlantData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WRITE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                    Cursor cursor = db.fetchTransporterDataToExport();

                    if(cursor.getCount() == 0) {
                        Toast.makeText(this, "No data to export", Toast.LENGTH_LONG).show();
                    } else {
                        // Write column names to file as a header
                        String[] columnNamesArray = cursor.getColumnNames();
                        String columnNamesString = TextUtils.join(",", columnNamesArray);
                        writer.write(columnNamesString + "\n");

                        // Write actual data to file
                        while(cursor.moveToNext()) {

                            String[] valuesArray = new String[cursor.getColumnCount()];

                            for(int i=0; i < cursor.getColumnCount(); i ++) {
                                valuesArray[i] = cursor.getString(i);
                            }

                            String valuesString = TextUtils.join(",", valuesArray);
                            writer.write(valuesString + "\n");
                        }
                    }
                    
                    writer.close();

                    Intent intent = new Intent(this, ExportPlantData.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast. makeText(getApplicationContext(),"Error exporting transporter data!",Toast. LENGTH_LONG).show();
                    Intent intent = new Intent(this, ExportTransporterData.class);
                    startActivity(intent);
                }
            } else {
                // If back button is pressed (no file is saved), go back to same page
                Intent intent = new Intent(this, ExportTransporterData.class);
                startActivity(intent);
            }
        }
    }

    public void goToExportPlantData() {
        Intent intent = new Intent(this, ExportPlantData.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // This page is automatically navigated to once all milk containers have been received.
        // We do not want to allow going back to the receiver homepage once this has happened
        // (the receiver homepage would just redirect us here anyway since all containers are collected)
    }

}

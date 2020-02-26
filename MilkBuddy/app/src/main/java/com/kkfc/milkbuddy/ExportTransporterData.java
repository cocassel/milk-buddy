package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

                    Cursor cursor = db.fetchTransporterData();
                    if(cursor.getCount() == 0) {
                        Toast.makeText(this, "No data to export", Toast.LENGTH_LONG).show();
                    } else {
                        while(cursor.moveToNext()) {
                            // 1 is the column for first name
                             String blah = cursor.getString(1) + " " + cursor.getString(2);
                        }
                    }

                    // TODO

                    writer.write("DO I WORK?, YES");
                    
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

    @Override
    public void onBackPressed() {
        // This page is automatically navigated to once all milk containers have been
    }

}

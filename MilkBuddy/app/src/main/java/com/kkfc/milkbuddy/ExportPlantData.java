package com.kkfc.milkbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExportPlantData extends AppCompatActivity {

    DatabaseHelper db;
    private static final int WRITE_REQUEST_CODE = 42;
    private Button exportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_plant_data);

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
                        .putExtra(Intent.EXTRA_TITLE, "Receiver Milk Log " + date);
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

                    Cursor cursor = db.fetchPlantDataToExport();

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

                    Intent intent = new Intent(this, Completed.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast. makeText(getApplicationContext(),"Error exporting plant data!",Toast. LENGTH_LONG).show();
                    Intent intent = new Intent(this, ExportPlantData.class);
                    startActivity(intent);
                }
            } else {
                // If back button is pressed (no file is saved), go back to same page
                Intent intent = new Intent(this, ExportPlantData.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Allow a user to go back to the ExportTransporterData page so that they can export again
        // if they so choose
        Intent intent = new Intent(this, ExportTransporterData.class);
        startActivity(intent);
    }
}
